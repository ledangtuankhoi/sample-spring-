package com.example.employee.Mappers;

import com.example.employee.DTO.EmployeeDTO;
import com.example.employee.Model.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
// @Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mappings(
        {
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "isDiscipilined", ignore = true),
            @Mapping(target = "KIN", ignore = true),
        }
    )
    EmployeeEntity toEntity(EmployeeDTO employeeDTO);

    @Mappings(
        {
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
        }
    )
    EmployeeDTO toDto(EmployeeEntity entity);
}
