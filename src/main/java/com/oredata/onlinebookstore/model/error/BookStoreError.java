package com.oredata.onlinebookstore.model.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


public enum BookStoreError {


    BAD_PARAM(BAD_REQUEST, 980002),
    BAD_PARAM_BODY(BAD_REQUEST, 980003),
    BAD_PARAM_VALIDATION(BAD_REQUEST, 980004),
    EMAIL_ALREADY_EXIST(BAD_REQUEST, 980004),
    ISBN_ALREADY_EXIST(BAD_REQUEST, 980004),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 980004),
    FORBIDDEN(HttpStatus.FORBIDDEN,98004),
    INSUFFICIENT_STOCK(BAD_REQUEST,98004),
    MINIMUM_PRICE_FOR_ORDER_IS_NOT_SUFFICIENT(BAD_REQUEST,98004),
    BOOK_NOT_FOUND(NOT_FOUND, 980101),
    USER_NOT_FOUND(NOT_FOUND, 980101);

    BookStoreError(HttpStatus badRequest, int code) {
    }
}
