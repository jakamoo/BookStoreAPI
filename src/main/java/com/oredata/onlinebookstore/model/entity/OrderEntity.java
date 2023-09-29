package com.oredata.onlinebookstore.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")
public class OrderEntity extends AbstractAuditableEntity {

    private Long userId;

    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @OneToMany(targetEntity = OrderRequestEntity.class)
    @JoinColumn(name = "orderId")
    private List<OrderRequestEntity> orderRequests;


}