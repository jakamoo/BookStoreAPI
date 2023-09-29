package com.oredata.onlinebookstore.apis;

import com.google.gson.Gson;
import com.oredata.onlinebookstore.model.dto.BookDTO;
import com.oredata.onlinebookstore.model.entity.BookEntity;
import com.oredata.onlinebookstore.repository.BookRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BookApiTest {

    private final BookDTO bookDTO = new BookDTO("bookIsbn","bookTitle","bookAuthor", new BigDecimal(12),3);
    private final Gson gson = new Gson();

    @Autowired
    private BookRepository bookRepository;


    @Test
    public void booksEndpointPostMethodShouldCreateBookWithValidData() {

        String jsonBookDTO = gson.toJson(bookDTO);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBookDTO)
                .when()
                .post("/books");

        Long bookId = Long.valueOf(response.jsonPath().getString("bookId"));


        BookEntity bookEntityFromDB = bookRepository.findById(bookId).get();

        Assertions.assertEquals(bookEntityFromDB.getStockQuantity(),bookDTO.getStockQuantity());
        Assertions.assertEquals(bookEntityFromDB.getIsbn(),bookDTO.getIsbn());
        Assertions.assertEquals(bookEntityFromDB.getAuthor(),bookDTO.getAuthor());
        Assertions.assertEquals(bookEntityFromDB.getTitle(),bookDTO.getTitle());
        Assertions.assertEquals(bookEntityFromDB.getPrice(),bookDTO.getPrice());


    }
}
