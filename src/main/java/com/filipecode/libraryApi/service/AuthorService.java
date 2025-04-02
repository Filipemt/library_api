package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }
}
