package com.filipecode.libraryApi.controller.mappers;

import com.filipecode.libraryApi.model.dtos.AuthorResponseDTO;
import com.filipecode.libraryApi.model.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Transforma em um componente Spring em tempo de compilação
public interface AuthorResponseMapper {

    Author toEntity(AuthorResponseDTO authorResponseDTO);

    AuthorResponseDTO toDTO(Author author);
}
