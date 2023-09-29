package com.oredata.onlinebookstore.controller;

import com.oredata.onlinebookstore.model.dto.OrderDTO;
import com.oredata.onlinebookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderRequests) {

        OrderDTO orderDTO = orderService.createOrder(orderRequests);
        return new ResponseEntity<>(orderDTO,HttpStatus.CREATED);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDTO>> retrieveOrderByUserId(@PathVariable Long userId) {

        List<OrderDTO> orderDTOList = orderService.retrieveOrderByUserId(userId);
        return new ResponseEntity<>(orderDTOList,HttpStatus.OK);
    }
}



