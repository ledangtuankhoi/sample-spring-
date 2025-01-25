package com.example.book.Model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.book.Controller.BookController;
import com.example.book.DTO.BookDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BookModelAssembler
    implements RepresentationModelAssembler<BookDTO, EntityModel<BookDTO>> {

    @Override
    public EntityModel<BookDTO> toModel(BookDTO entity) {
        return EntityModel.of(
            entity,
            linkTo(
                methodOn(BookController.class).get(entity.getId())
            ).withSelfRel(),
            linkTo(methodOn(BookController.class).all()).withRel("books")
        );
    }
}
