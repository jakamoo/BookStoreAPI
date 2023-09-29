package com.oredata.onlinebookstore.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "books")
@Data
public class BookEntity extends AbstractAuditableEntity {

    @Column(nullable = false, unique = true)
    private String isbn;

    private String title;

    private String author;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stockQuantity;



}
