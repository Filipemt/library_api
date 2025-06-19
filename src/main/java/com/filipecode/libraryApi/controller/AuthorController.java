package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.AuthorMapper;
import com.filipecode.libraryApi.controller.mappers.AuthorResponseMapper;
import com.filipecode.libraryApi.model.dtos.request.AuthorDTO;
import com.filipecode.libraryApi.model.dtos.response.AuthorResponseDTO;
import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final AuthorResponseMapper authorResponseMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorService.save(author);

        URI location = headerLocation(author.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'OPERADOR')")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable String id) {
        var authorId = UUID.fromString(id);

        return authorService.getById(authorId)
                .map(author -> {
                    AuthorResponseDTO authorResponseDTO = authorResponseMapper.toDTO(author);
                    return ResponseEntity.ok(authorResponseDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'OPERADOR')")
    public ResponseEntity<List<AuthorResponseDTO>> listAuthor(@RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "nationality", required = false) String nationality) {

        List<Author> searchResult = authorService.filterAuthor(name, nationality);
        List<AuthorResponseDTO> list = searchResult
                .stream()
                .map(authorResponseMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable String id) {
        UUID authorId = UUID.fromString(id);
        Optional<Author> optionalAuthor = authorService.getById(authorId);

        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authorService.deleteById(optionalAuthor.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> updateAuthor(@PathVariable String id,
                                             @RequestBody @Valid AuthorDTO AuthorDTO) {

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

    }
}
