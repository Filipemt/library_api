package com.filipecode.libraryApi.controller.common;

import com.filipecode.libraryApi.exceptions.DuplicateRegisterException;
import com.filipecode.libraryApi.exceptions.OperationNotAllowedException;
import com.filipecode.libraryApi.model.dtos.ErrorResponseDTO;
import com.filipecode.libraryApi.model.dtos.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<FieldError> fieldError = exception.getFieldErrors();
        List<FieldErrorDTO> errorList = fieldError
                .stream()
                .map(fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());


        return new ErrorResponseDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Error validation",
                errorList
                );
    }

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleDuplicateRegisterException(DuplicateRegisterException e) {
        return ErrorResponseDTO.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleOperationNotAllowedException(OperationNotAllowedException e) {
        return ErrorResponseDTO.patternResponse(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleInternalServerError(RuntimeException e) {
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error. Contact the system administration",
                List.of());
    }
}
