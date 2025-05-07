package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.BookMapper;
import com.filipecode.libraryApi.model.dtos.request.RegisterBookDTO;
import com.filipecode.libraryApi.model.dtos.response.ResultResearchBookDTO;
import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.model.enums.BookGender;
import com.filipecode.libraryApi.service.BookService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable String id) {
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Page<ResultResearchBookDTO>> search(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "gender", required = false) BookGender gender,
            @RequestParam(value = "publication-date", required = false) Integer publicationDate,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ) {
        Page<Book> resultPage = bookService.search(isbn, title, authorName, gender, publicationDate, page, pageSize);

        Page<ResultResearchBookDTO> result = resultPage.map(bookMapper::toDTO);

        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid RegisterBookDTO registerBookDTO) {
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    Book mapperEntity = bookMapper.toEntity(registerBookDTO);
                    book.setPublicationDate(mapperEntity.getPublicationDate());
                    book.setIsbn(mapperEntity.getIsbn());
                    book.setPrice(mapperEntity.getPrice());
                    book.setGender(mapperEntity.getGender());
                    book.setTitle(mapperEntity.getTitle());
                    book.setAuthor(mapperEntity.getAuthor());

                    bookService.update(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
