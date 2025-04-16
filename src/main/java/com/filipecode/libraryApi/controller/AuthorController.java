package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.AuthorMapper;
import com.filipecode.libraryApi.controller.mappers.AuthorResponseMapper;
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
    private final AuthorMapper authorMapper;
    private final AuthorResponseMapper authorResponseMapper;

    @PostMapping
    public ResponseEntity<Object> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        try {
            Author author = authorMapper.toEntity(authorDTO);
            authorService.save(author);

            // http://localhost:8080/autores/{id do usuário criado}
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(author.getId())
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

        return authorService.getById(authorId)
                .map(author -> {
                    AuthorResponseDTO authorResponseDTO = authorResponseMapper.toDTO(author);
                    return ResponseEntity.ok(authorResponseDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build());
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
                .map(authorResponseMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable String id,
                                             @RequestBody @Valid AuthorDTO AuthorDTO) {

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
