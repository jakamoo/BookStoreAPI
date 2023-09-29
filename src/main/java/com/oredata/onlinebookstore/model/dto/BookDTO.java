package com.oredata.onlinebookstore.model.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class BookDTO {
    private String isbn;

    private String title;

    private String author;
    private BigDecimal price;
    private Integer stockQuantity;

    public BookDTO(String isbn, String title, String author, BigDecimal price, Integer stockQuantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
