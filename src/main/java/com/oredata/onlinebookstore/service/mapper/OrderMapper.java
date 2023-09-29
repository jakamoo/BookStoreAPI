package com.oredata.onlinebookstore.service.mapper;

import com.oredata.onlinebookstore.model.dto.OrderDTO;
import com.oredata.onlinebookstore.model.entity.OrderEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final ModelMapper modelMapper;



    public OrderDTO entityToDto(OrderEntity orderEntity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    public OrderEntity dtoToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, OrderEntity.class);
    }
}
