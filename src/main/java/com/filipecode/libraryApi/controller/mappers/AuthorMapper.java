package com.filipecode.libraryApi.controller.mappers;

import com.filipecode.libraryApi.model.dtos.request.AuthorDTO;
import com.filipecode.libraryApi.model.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Transforma em um componente Spring em tempo de compilação
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);

    AuthorDTO toDTO(Author author);
}
