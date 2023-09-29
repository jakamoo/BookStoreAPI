package com.oredata.onlinebookstore.exception;

import com.oredata.onlinebookstore.model.error.BookStoreError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;

public class CustomRestException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BookNotFoundRestException extends BookStoreServiceRestException {
        public BookNotFoundRestException(String isbn) {
            super(BookStoreError.BOOK_NOT_FOUND, String.format("Book with ID %s is not found", isbn));
        }
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class UserAlreadyExistRestException extends BookStoreServiceRestException {
        public UserAlreadyExistRestException(String email) {
            super(BookStoreError.EMAIL_ALREADY_EXIST, String.format("User with %s email is already exist", email));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class InSufficientStockRestException extends BookStoreServiceRestException {
        public InSufficientStockRestException(List<String> inSufficientStocks) {
            super(BookStoreError.INSUFFICIENT_STOCK, String.format("Stock with %s isbn is not sufficient", inSufficientStocks));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class  MinimumPriceForOrderIsNotSufficientRestException extends BookStoreServiceRestException {
        public MinimumPriceForOrderIsNotSufficientRestException(BigDecimal price) {
            super(BookStoreError.MINIMUM_PRICE_FOR_ORDER_IS_NOT_SUFFICIENT, String.format("Order must not be lower than %s", price));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class   UserNotFoundRestException extends BookStoreServiceRestException {
        public  UserNotFoundRestException(Long userId) {
            super(BookStoreError.USER_NOT_FOUND, String.format("User with ID %s is not found", userId));
        }
    }




    @ResponseStatus(HttpStatus.CONFLICT)
    public static class BookAlreadyExistRestException extends BookStoreServiceRestException {
        public BookAlreadyExistRestException(String isbn) {
            super(BookStoreError.ISBN_ALREADY_EXIST, String.format("Book with %s isbn is already exist", isbn));
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnAuthorizedRestException extends BookStoreServiceRestException {
        public UnAuthorizedRestException() {
            super(BookStoreError.UNAUTHORIZED, "User is not authorized to perform this action");
        }

    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class ForbiddenRestException extends BookStoreServiceRestException {
        public  ForbiddenRestException() {
            super(BookStoreError.FORBIDDEN, "User is forbidden to perform this action");
        }
    }

}
