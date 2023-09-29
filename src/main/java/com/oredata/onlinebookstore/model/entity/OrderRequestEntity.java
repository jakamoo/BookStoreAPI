package com.oredata.onlinebookstore.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_requests")
@Data
public class OrderRequestEntity extends AbstractAuditableEntity{
    @Column(nullable = false, unique = true)
    private String isbn;
    @Column(nullable = false)
    private int quantity;

    private Long orderId;
}
