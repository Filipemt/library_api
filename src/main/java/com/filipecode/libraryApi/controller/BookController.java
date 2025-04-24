package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.BookMapper;
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
public class BookController implements GenericController{

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Void> saveBook(@RequestBody @Valid RegisterBookDTO registerBookDTO) {

        Book book = bookMapper.toEntity(registerBookDTO);
        bookService.save(book);

        var url = headerLocation(book.getId());

        return ResponseEntity.created(url).build();


    }
}
