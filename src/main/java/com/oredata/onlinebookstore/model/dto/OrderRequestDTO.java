package com.oredata.onlinebookstore.model.dto;

import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;

@Data
public class OrderRequestDTO {
    @Unique
    private String isbn;
    private int quantity;
}
