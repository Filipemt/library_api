package com.filipecode.libraryApi.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AuthorDTO(
        @NotBlank(message = "field mandatory")
        @Size(max = 100, message = "name must have a maximum of 100 characters")
        String name,
        @NotNull(message = "field mandatory")
        @Past(message = "can´t be a future date")
        LocalDate dateOfBirth,
        @NotBlank(message = "field mandatory")
        @Size(max = 50, message = "nationality must have a maximum of 50 characters")
        String nationality){
}
