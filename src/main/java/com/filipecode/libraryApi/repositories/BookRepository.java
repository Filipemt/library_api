package com.filipecode.libraryApi.repositories;

import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    // Query Method
    // SELECT * FROM livro WHERE id_autor = id
    List<Book> findByAuthor(Author author);

    // SELECT * from livro WHERE title = title
    List<Book> findByTitle(String title);

    boolean existsByAuthor(Author author);
}
