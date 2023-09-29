package com.oredata.onlinebookstore.model.dto;

import com.oredata.onlinebookstore.model.entity.BookEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long userId;

    private BigDecimal totalPrice;


    private List<OrderRequestDTO> orderRequestDTOs;

}
