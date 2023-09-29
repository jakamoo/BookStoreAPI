package com.oredata.onlinebookstore.service;

import com.oredata.onlinebookstore.exception.CustomRestException.UserNotFoundRestException;
import com.oredata.onlinebookstore.exception.CustomRestException.BookNotFoundRestException;
import com.oredata.onlinebookstore.exception.CustomRestException.MinimumPriceForOrderIsNotSufficientRestException;
import com.oredata.onlinebookstore.exception.CustomRestException.InSufficientStockRestException;
import com.oredata.onlinebookstore.model.dto.OrderDTO;
import com.oredata.onlinebookstore.model.dto.OrderRequestDTO;
import com.oredata.onlinebookstore.model.entity.BookEntity;
import com.oredata.onlinebookstore.model.entity.OrderEntity;
import com.oredata.onlinebookstore.repository.BookRepository;
import com.oredata.onlinebookstore.repository.OrderRepository;
import com.oredata.onlinebookstore.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final BigDecimal MINIMUM_ORDER_PRICE = new BigDecimal(25);


    private final OrderRepository orderRepository;


    private final BookRepository bookRepository;

    private final OrderMapper orderMapper;

    public OrderDTO createOrder(@NotNull OrderDTO orderDTO) {
        checkStock(orderDTO.getOrderRequestDTOs());
        checkMinimumPayment(orderDTO.getOrderRequestDTOs());
        int stockQuantity;

        List<OrderRequestDTO> orderRequestDTOs = orderDTO.getOrderRequestDTOs();

        for (OrderRequestDTO orderRequestDTO : orderRequestDTOs) {
            String orderedIsbn = orderRequestDTO.getIsbn();
            int orderedStockQuantity = orderRequestDTO.getQuantity();
            Optional<BookEntity> optionalBookEntity = bookRepository.findByIsbn(orderedIsbn);
            if(optionalBookEntity.isPresent()) {
                stockQuantity = optionalBookEntity.get().getStockQuantity();
            }else throw new BookNotFoundRestException(orderedIsbn);

            optionalBookEntity.get().setStockQuantity(stockQuantity - orderedStockQuantity);
        }


        OrderEntity orderEntity = orderRepository.save(orderMapper.dtoToEntity(orderDTO));

        return orderMapper.entityToDto(orderEntity);
    }

    private void checkStock(@NotNull List<OrderRequestDTO> orders) {

        List<String> insufficientStocks = new ArrayList<>();
        int stockQuantity;

        for (OrderRequestDTO order : orders) {
            String orderedIsbn = order.getIsbn();
            int orderedStockQuantity = order.getQuantity();

            if (bookRepository.findByIsbn(orderedIsbn).isPresent()) {
                stockQuantity = bookRepository.findByIsbn(orderedIsbn).get().getStockQuantity();
            } else
                throw new BookNotFoundRestException(orderedIsbn);


            if (orderedStockQuantity > stockQuantity) {
                insufficientStocks.add(orderedIsbn);
            }

        }

        if (!insufficientStocks.isEmpty())
            throw new InSufficientStockRestException(insufficientStocks);

    }

    private void checkMinimumPayment(@NotNull List<OrderRequestDTO> orders) {

        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal price;

        for (OrderRequestDTO order : orders) {
            String orderedIsbn = order.getIsbn();
            int orderedStockQuantity = order.getQuantity();
            if (bookRepository.findByIsbn(orderedIsbn).isPresent()) {
                price = bookRepository.findByIsbn(orderedIsbn).get().getPrice();
            } else throw new BookNotFoundRestException(orderedIsbn);
            totalAmount = totalAmount.add(price.multiply(new BigDecimal(orderedStockQuantity)));
        }

        if (totalAmount.compareTo(new BigDecimal(25)) < 0)
            throw new MinimumPriceForOrderIsNotSufficientRestException(MINIMUM_ORDER_PRICE);


    }

    public List<OrderDTO> retrieveOrderByUserId(@NotNull Long userId){
        List<OrderEntity> orderEntityList= orderRepository.findAllByUserId(userId);
        if(!orderEntityList.isEmpty()){

            return orderEntityList.stream()
                    .sorted(Comparator.comparing(OrderEntity::getUpdatedAt).reversed())
                    .map(orderMapper::entityToDto)
                    .toList();
        }throw new UserNotFoundRestException(userId);

    }
}
