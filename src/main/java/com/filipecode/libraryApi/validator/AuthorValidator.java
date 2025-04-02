package com.filipecode.libraryApi.validator;

import com.filipecode.libraryApi.exceptions.DuplicateRegisterException;
import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private boolean existsAuthorRegistered(Author author) {
        Optional<Author> foundedAuthor = authorRepository.findByNameAndDateOfBirthAndNationality(
                author.getName(),
                author.getDateOfBirth(),
                author.getNationality()
        );

        if (author.getId() == null) {
            return foundedAuthor.isPresent();
        }

        return !author.getId().equals(foundedAuthor.get().getId()) && foundedAuthor.isPresent();
    }

    public void validate(Author author) {
        if (existsAuthorRegistered(author)) {
            throw new DuplicateRegisterException("Duplicated register");
        }
    }
}
