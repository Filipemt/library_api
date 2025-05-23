package com.filipecode.libraryApi.repositories;

import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    // Query Method
    // SELECT * FROM livro WHERE id_autor = id
    List<Book> findByAuthor(Author author);

    // SELECT * from livro WHERE title = title
    List<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

    boolean existsByAuthor(Author author);
}
