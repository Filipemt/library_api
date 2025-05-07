package com.filipecode.libraryApi.model.dtos.request;

import com.filipecode.libraryApi.model.enums.BookGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterBookDTO(@NotBlank(message = "field mandatory") @ISBN String isbn,
                              @NotBlank(message = "field mandatory") String title,
                              @NotNull(message = "field mandatory") @Past(message = "can´t be a future date") LocalDate publicationDate,
                              BookGender gender,
                              BigDecimal price,
                              @NotNull(message = "field mandatory") UUID idAuthor) {
}
