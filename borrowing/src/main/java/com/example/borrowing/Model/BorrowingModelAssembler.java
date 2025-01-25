package com.example.borrowing.Model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.borrowing.Controller.BorrowingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BorrowingModelAssembler
    implements
        RepresentationModelAssembler<
            BorrowingEntity,
            EntityModel<BorrowingEntity>
        > {

    @Override
    public EntityModel<BorrowingEntity> toModel(BorrowingEntity entity) {
        return EntityModel.of(
            entity,
            // linkTo(methodOn(BookController.class).get(entity.getId())).withSelfRel(),
            linkTo(methodOn(BorrowingController.class).GetAll()).withRel(
                "borrowings"
            )
        );
    }
    // public EntityModel<BookDTO> toBooks(BookEntity entity) {
    //     return bookModelAssembler.toModel(bookMapper.toDto(entity));
    // }
}
