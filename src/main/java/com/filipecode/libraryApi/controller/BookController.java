package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.BookMapper;
import com.filipecode.libraryApi.exceptions.DuplicateRegisterException;
import com.filipecode.libraryApi.model.dtos.ErrorResponseDTO;
import com.filipecode.libraryApi.model.dtos.RegisterBookDTO;
import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid RegisterBookDTO registerBookDTO) {
        try {
            Book book = bookMapper.toEntity(registerBookDTO);
            bookService.save(book);
            return ResponseEntity.ok(book);

        } catch (DuplicateRegisterException e) {
            var errorDTO = ErrorResponseDTO.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
