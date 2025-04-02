package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.model.dtos.AuthorDTO;
import com.filipecode.libraryApi.model.dtos.AuthorResponseDTO;
import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody AuthorDTO author) {
        Author authorEntity = author.mapping();
        authorService.save(authorEntity);

        // http://localhost:8080/autores/{id do usuário criado}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable String id) {
        var authorId = UUID.fromString(id);
        Optional<Author> optionalAuthor = authorService.getById(authorId);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO(author.getId(),
                    author.getName(),
                    author.getDateOfBirth(),
                    author.getNationality());

            return ResponseEntity.ok(authorResponseDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable String id) {
        UUID authorId = UUID.fromString(id);
        Optional<Author> optionalAuthor = authorService.getById(authorId);

        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authorService.deleteById(optionalAuthor.get());
        return ResponseEntity.noContent().build();
    }
}
