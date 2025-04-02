package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> getById(UUID id) {
        return authorRepository.findById(id);
    }

    public void deleteById(Author author) {
        authorRepository.delete(author);
    }

    public List<Author> filterAuthor(String name, String nationality) {
        if (name != null && nationality != null) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }
        if (name != null) {
            return authorRepository.findByName(name);
        }
        if (nationality != null) {
            return authorRepository.findByNationality(nationality);
        }
        return authorRepository.findAll();
    }
}
