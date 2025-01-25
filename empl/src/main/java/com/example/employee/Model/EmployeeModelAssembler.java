package com.example.employee.Model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.employee.Controller.EmployeeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EmployeeModelAssembler
    implements
        RepresentationModelAssembler<
            EmployeeEntity,
            EntityModel<EmployeeEntity>
        > {

    @Override
    public EntityModel<EmployeeEntity> toModel(EmployeeEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("EmployeeEntity cannot be null");
        }

        return EntityModel.of(
            entity,
            linkTo(
                methodOn(EmployeeController.class).get(entity.getId())
            ).withSelfRel(),
            linkTo(methodOn(EmployeeController.class).getAll12()).withRel(
                "employees"
            )
        );
    }
}
