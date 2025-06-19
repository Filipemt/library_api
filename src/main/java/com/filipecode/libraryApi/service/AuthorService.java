package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.exceptions.OperationNotAllowedException;
import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.repositories.AuthorRepository;
import com.filipecode.libraryApi.repositories.BookRepository;
import com.filipecode.libraryApi.security.SecurityService;
import com.filipecode.libraryApi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;
    private final SecurityService securityService;

    public Author save(Author author) {
        authorValidator.validate(author);
        author.setUserLogin(securityService.getByLoggedUser());
        return authorRepository.save(author);
    }

    public void update(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("For update, it´s necessary author in database.");
        }

        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public Optional<Author> getById(UUID id) {
        return authorRepository.findById(id);
    }

    public void deleteById(Author author) {
        if (authorHasBook(author)) {
            throw new OperationNotAllowedException("Author of registered books");
        }
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

    public boolean authorHasBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
