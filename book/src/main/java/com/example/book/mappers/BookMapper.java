package com.example.book.mappers;

import com.example.book.dtos.BookDTO;
import com.example.book.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mappings(
        {
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
        }
    )
    BookEntity toEntity(BookDTO bookDTO);

    @Mappings(
        {
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "author", target = "author"),
            @Mapping(source = "isReady", target = "isReady"),
        }
    )
    BookDTO toDto(BookEntity bookEntity);
}
