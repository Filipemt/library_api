package com.filipecode.libraryApi.controller.mappers;

import com.filipecode.libraryApi.model.dtos.request.UserDTO;
import com.filipecode.libraryApi.model.entities.UserLogin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserLogin toEntity(UserDTO dto);
}
