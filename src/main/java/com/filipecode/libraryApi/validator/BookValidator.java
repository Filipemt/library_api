package com.filipecode.libraryApi.validator;

import com.filipecode.libraryApi.exceptions.DuplicateRegisterException;
import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;

    public void validate(Book book) {
        if (existsBookWithIsbn(book)) {
            throw new DuplicateRegisterException("ISBN already exists!");
        }
    }

    private boolean existsBookWithIsbn(Book book) {
        Optional<Book> foundedBook = bookRepository.findByIsbn(book.getIsbn()); // Livro que vem do banco de dados

        if (book.getId() == null) {
            return foundedBook.isPresent();
        }

        return foundedBook
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
