package com.filipecode.libraryApi.model.dtos;

import com.filipecode.libraryApi.model.enums.BookGender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultResearchBookDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        AuthorDTO author
) {
}
