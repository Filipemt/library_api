package com.filipecode.libraryApi.controller.mappers;

import com.filipecode.libraryApi.model.dtos.request.RegisterBookDTO;
import com.filipecode.libraryApi.model.dtos.response.ResultResearchBookDTO;
import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.repositories.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java(authorRepository.findById(registerBookDTO.idAuthor()).orElse(null))")
    public abstract Book toEntity(RegisterBookDTO registerBookDTO);

    public abstract ResultResearchBookDTO toDTO(Book book);
}
