package com.filipecode.libraryApi.controller.mappers;

import com.filipecode.libraryApi.model.dtos.request.UserLoginDTO;
import com.filipecode.libraryApi.model.entities.UserLogin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserLoginMapper {

    UserLogin toEntity(UserLoginDTO dto);
}
