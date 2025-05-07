package com.filipecode.libraryApi.model.dtos.response;

import com.filipecode.libraryApi.model.dtos.request.AuthorDTO;
import com.filipecode.libraryApi.model.enums.BookGender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultResearchBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        AuthorDTO author
) {
}
