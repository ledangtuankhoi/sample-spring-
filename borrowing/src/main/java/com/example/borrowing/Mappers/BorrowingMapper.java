package com.example.borrowing.Mappers;

import com.example.borrowing.DTO.BorrowingDTO;
import com.example.borrowing.Model.BorrowingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BorrowingMapper {
    @Mappings(
        {
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
        }
    )
    BorrowingEntity toEntity(BorrowingDTO borrowingDTO);

    @Mappings(
        {
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "returnBorrowing", target = "returnBorrowing"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "bookId", target = "bookId"),
            @Mapping(source = "employeeId", target = "employeeId"),
        }
    )
    BorrowingDTO toDto(BorrowingEntity borrowingEntity);
}
