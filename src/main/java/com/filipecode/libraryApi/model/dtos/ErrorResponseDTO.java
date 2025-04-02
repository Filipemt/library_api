package com.filipecode.libraryApi.model.dtos;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponseDTO(int status, String message, List<FieldErrorDTO> errors) {

    public static ErrorResponseDTO patternResponse(String message) {
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErrorResponseDTO conflict(String message) {
        return new ErrorResponseDTO(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
