package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.model.enums.BookGender;
import com.filipecode.libraryApi.repositories.BookRepository;
import com.filipecode.libraryApi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.filipecode.libraryApi.repositories.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    public Book save(Book book) {
        bookValidator.validate(book);
        return bookRepository.save(book);
    }

    public Optional<Book> getById(UUID id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    // Isbn, Titulo, Nome Autor, genero e ano de publicação
    public List<Book> search(
            String isbn, String title, String authorName, BookGender gender, Integer publicationDate
    ) {
//        Specification<Book> specs = Specification
//                .where(BookSpecs.isbnEqual(isbn))
//                .and(BookSpecs.titleLike(title))
//                .and(Book)
//                .and(BookSpecs.genderEqual(gender))
//                .and(BookSpecs.publicationDateEqual(publicationDate));
//

        // SELECT * FROM livro WHERE 0 = 0
        Specification<Book> specification = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (isbn != null) {
            specification = specification.and(isbnEqual(isbn));
        }
        if (title != null) {
            specification = specification.and(titleLike(title));
        }
        if (gender != null) {
            specification = specification.and(genderEqual(gender));
        }
        if (publicationDate != null) {
            specification = specification.and(publicationDateEqual(publicationDate));
        }
        if (authorName != null) {
            specification = specification.and(authorNameLike(authorName));
        }

        return bookRepository.findAll(specification);
    }

    public void update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("For update, it´s necessary book in database.");
        }

        bookValidator.validate(book);
        bookRepository.save(book);
    }
}
