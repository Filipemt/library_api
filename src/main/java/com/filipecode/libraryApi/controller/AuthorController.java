package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.exceptions.DuplicateRegisterException;
import com.filipecode.libraryApi.exceptions.OperationNotAllowedException;
import com.filipecode.libraryApi.model.dtos.AuthorDTO;
import com.filipecode.libraryApi.model.dtos.AuthorResponseDTO;
import com.filipecode.libraryApi.model.dtos.ErrorResponseDTO;
import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Object> createAuthor(@RequestBody @Valid AuthorDTO author) {
        try {
            Author authorEntity = author.mapping();
            authorService.save(authorEntity);

            // http://localhost:8080/autores/{id do usuário criado}
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorEntity.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRegisterException e) {
            var errorResponseDTO = ErrorResponseDTO.conflict(e.getMessage());
            return ResponseEntity.status(errorResponseDTO.status()).body(errorResponseDTO);
        }
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
    public ResponseEntity<Object> deleteAuthorById(@PathVariable String id) {
        try {
            UUID authorId = UUID.fromString(id);
            Optional<Author> optionalAuthor = authorService.getById(authorId);

            if (optionalAuthor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            authorService.deleteById(optionalAuthor.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowedException e) {
            var errorResponse = ErrorResponseDTO.patternResponse(e.getMessage());
            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> listAuthor(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "nationality", required = false) String nationality) {

        List<Author> searchResult = authorService.filterAuthor(name, nationality);
        List<AuthorResponseDTO> list = searchResult
                .stream()
                .map(author -> new AuthorResponseDTO(
                        author.getId(),
                        author.getName(),
                        author.getDateOfBirth(),
                        author.getNationality())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable String id,
                                             @RequestBody AuthorDTO AuthorDTO) {

        try {
            UUID authorId = UUID.fromString(id);
            Optional<Author> optionalAuthor = authorService.getById(authorId);

            if (optionalAuthor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var author = optionalAuthor.get();
            author.setName(AuthorDTO.name());
            author.setNationality(AuthorDTO.nationality());
            author.setDateOfBirth(AuthorDTO.dateOfBirth());

            authorService.update(author);

            return ResponseEntity.noContent().build();
        } catch (DuplicateRegisterException e) {
            var errorResponseDTO = ErrorResponseDTO.conflict(e.getMessage());
            return ResponseEntity.status(errorResponseDTO.status()).body(errorResponseDTO);
        }
    }
}
