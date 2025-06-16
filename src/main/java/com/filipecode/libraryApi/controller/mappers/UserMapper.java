package com.filipecode.libraryApi.controller.mappers;

import com.filipecode.libraryApi.model.dtos.request.UserDTO;
import com.filipecode.libraryApi.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
}
