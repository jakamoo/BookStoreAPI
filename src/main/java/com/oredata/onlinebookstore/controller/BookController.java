package com.oredata.onlinebookstore.controller;

import com.oredata.onlinebookstore.model.dto.BookDTO;
import com.oredata.onlinebookstore.service.BookService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO book,@RequestHeader("Authorization") String authHeader) {
        BookDTO savedBook = bookService.createBook(book,authHeader);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO book,@RequestHeader("Authorization") String authHeader,@PathVariable String isbn) {
        BookDTO savedBook = bookService.updateBook(book,authHeader,isbn);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }
    @DeleteMapping("/{isbn}")
    public ResponseEntity<BookDTO>deleteBook(@RequestHeader("Authorization") String authHeader,@PathVariable String isbn) {
        bookService.deleteBook(authHeader,isbn);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> retrieveBookPages(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable =  PageRequest.of(page, size, Sort.by(sortBy));

        Page<BookDTO> books = bookService.retrieveBookList(pageable);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO>retrieveBook(@PathVariable String isbn) {
        BookDTO bookDTO=bookService.retrieveBook(isbn);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }
}
