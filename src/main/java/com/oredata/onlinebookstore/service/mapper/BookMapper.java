package com.oredata.onlinebookstore.service.mapper;

import com.oredata.onlinebookstore.model.dto.BookDTO;
import com.oredata.onlinebookstore.model.entity.BookEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {
    private final ModelMapper modelMapper;



    public BookDTO entityToDto(BookEntity bookEntity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(bookEntity, BookDTO.class);
    }

    public BookEntity dtoToEntity(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, BookEntity.class);
    }
}
