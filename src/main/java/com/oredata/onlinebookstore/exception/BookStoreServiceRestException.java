package com.oredata.onlinebookstore.exception;

import com.oredata.onlinebookstore.model.error.BookStoreError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class BookStoreServiceRestException extends RuntimeException{
    private final BookStoreError bookStoreError;
    private final String description;

}
