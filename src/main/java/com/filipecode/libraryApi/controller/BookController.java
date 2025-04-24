package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.BookMapper;
import com.filipecode.libraryApi.model.dtos.RegisterBookDTO;
import com.filipecode.libraryApi.model.dtos.ResultResearchBookDTO;
import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.service.BookService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("{id}")
    public ResponseEntity<ResultResearchBookDTO> getBookById(@PathVariable String id, ServletRequest servletRequest) {
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    var dto = bookMapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
