package com.filipecode.libraryApi.model.dtos.response;

import java.time.LocalDate;
import java.util.UUID;


public record AuthorResponseDTO(UUID id, String name, LocalDate dateOfBirth, String nationality) {

}
