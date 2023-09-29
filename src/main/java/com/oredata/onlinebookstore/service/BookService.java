package com.oredata.onlinebookstore.service;

import com.oredata.onlinebookstore.exception.CustomRestException.BookNotFoundRestException;
import com.oredata.onlinebookstore.exception.CustomRestException.ForbiddenRestException;
import com.oredata.onlinebookstore.exception.CustomRestException.UnAuthorizedRestException;
import com.oredata.onlinebookstore.exception.CustomRestException.BookAlreadyExistRestException;

import com.oredata.onlinebookstore.model.dto.BookDTO;
import com.oredata.onlinebookstore.model.entity.BookEntity;
import com.oredata.onlinebookstore.repository.BookRepository;
import com.oredata.onlinebookstore.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final JwtUtils jwtUtils;

    private void validateUser(@NotNull String authHeader) throws UnAuthorizedRestException, ForbiddenRestException {

        String token = authHeader.substring(7);
        boolean validateJwtToken = jwtUtils.validateToken(token);

        if (!validateJwtToken) {
            throw new UnAuthorizedRestException();
        }

        Authentication auth = jwtUtils.getAuthentication(authHeader);
        SecurityContextHolder.getContext().setAuthentication(auth);

        if (auth.getAuthorities().stream().noneMatch(r -> r.getAuthority().equals("ADMIN"))) {
            throw new ForbiddenRestException();
        }
    }


    public BookDTO createBook(BookDTO bookDto, String authHeader) {

        validateUser(authHeader);

        BookEntity bookEntity = bookMapper.dtoToEntity(bookDto);
        bookEntity.setCreatedAt(LocalDate.now());

        try {
            bookRepository.save(bookEntity);
            return bookMapper.entityToDto(bookEntity);

        } catch (DataIntegrityViolationException e) {
            throw new BookAlreadyExistRestException(bookDto.getIsbn());
        }
    }

    public BookDTO updateBook(BookDTO givenBookDto, String authHeader, String isbn) {

        validateUser(authHeader);

        Optional<BookEntity> optionalBookEntity= bookRepository.findByIsbn(isbn);

        if (optionalBookEntity.isEmpty())
            throw new BookNotFoundRestException(isbn);

        BookEntity bookEntity = optionalBookEntity.get();

        BookEntity givenBookEntity = bookMapper.dtoToEntity(givenBookDto);

        bookEntity.setUpdatedAt(LocalDate.now());
        bookEntity.setAuthor(givenBookEntity.getAuthor());
        bookEntity.setTitle(givenBookEntity.getTitle());
        bookEntity.setPrice(givenBookEntity.getPrice());
        bookEntity.setStockQuantity(givenBookEntity.getStockQuantity());




        try {
            bookRepository.save(bookEntity);
            return bookMapper.entityToDto(bookEntity);

        } catch (DataIntegrityViolationException e) {
            throw new BookAlreadyExistRestException(givenBookDto.getIsbn());
        }
    }

    public Page<BookDTO> retrieveBookList(Pageable pageable){
        Page<BookEntity> bookList= bookRepository.findAll(pageable);

        return bookList.map(bookMapper::entityToDto);
    }
    public BookDTO retrieveBook(String isbn){
       Optional <BookEntity> optionalBookEntity= bookRepository.findByIsbn(isbn);

        if(optionalBookEntity.isEmpty())
            throw new BookNotFoundRestException(isbn);
        BookEntity bookEntity = optionalBookEntity.get();
        return bookMapper.entityToDto(bookEntity);
    }
    public void deleteBook(String authHeader, String isbn) {

        validateUser(authHeader);

        Optional<BookEntity> optionalBookEntity= bookRepository.findByIsbn(isbn);

        if (optionalBookEntity.isEmpty())
            throw new BookNotFoundRestException(isbn);
        BookEntity bookEntity = optionalBookEntity.get();
       bookRepository.delete(bookEntity);


    }

}
