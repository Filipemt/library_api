package com.filipecode.libraryApi.model.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserLoginDTO(
        @NotBlank(message = "field mandatory")
        String login,
        @Email(message = "invalid email")
        @NotBlank
        String email,
        @NotBlank(message = "field mandatory")
        String password,
        List<String> roles
) {
}
