package com.oredata.onlinebookstore.repository;

import com.oredata.onlinebookstore.model.entity.BookEntity;
import com.oredata.onlinebookstore.model.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByIsbn(String isbn);

    @NotNull Page<BookEntity> findAll(Pageable pageable);


}
