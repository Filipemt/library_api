package com.filipecode.libraryApi.repositories;

import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.model.enums.BookGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void saveTest() {
        Author author = new Author();

        author.setName("Anne Frank");
        author.setNationality("Germany");
        author.setDateOfBirth(LocalDate.of(1929, 6, 12));

        var saveAuthor = repository.save(author);
        System.out.println(saveAuthor);
    }

    @Test
    public void updateTest() {
        var id = UUID.fromString("d0cb7815-38dd-46b3-9100-4eb86601adda");

        Optional<Author> possibleAuthor = repository.findById(id);

        if (possibleAuthor.isPresent()) {
            Author findAuthor = possibleAuthor.get();

            System.out.println("Author data: ");
            System.out.println(findAuthor);

            findAuthor.setName("Filipe Barbosa");

            repository.save(findAuthor);
        } else {
            System.out.println("Autor não encontrado!");
        }
    }

    @Test
    public void listTest() {
        List<Author> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Users count: " + repository.count());
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("d0cb7815-38dd-46b3-9100-4eb86601adda");

        repository.deleteById(id);
    }

    @Test
    public void deleteByObject() {
        var id = UUID.fromString("3133bf78-33d0-4c08-aacb-c114724ca438");

        var user = repository.findById(id).get();
        repository.delete(user);
    }

    @Test
    public void saveAuthorWithBookTest() {
        Author author = new Author();

        author.setName("Filipe Mota");
        author.setNationality("Brazilian");
        author.setDateOfBirth(LocalDate.of(2006, 1, 3));

        Book book = new Book();

        book.setIsbn("99999-99999");
        book.setTitle("Diário do Filipe");
        book.setPublicationDate(LocalDate.of(2024, 6, 25));
        book.setGender(BookGender.BIOGRAFIA);
        book.setPrice(BigDecimal.valueOf(55));
        book.setAuthor(author);

        Book book2 = new Book();

        book2.setIsbn("88888-88888");
        book2.setTitle("O roubo da casa assombrada");
        book2.setPublicationDate(LocalDate.of(2024, 8, 3));
        book2.setGender(BookGender.MISTERIO);
        book2.setPrice(BigDecimal.valueOf(70));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        repository.save(author);

        bookRepository.saveAll(author.getBooks());
    }

    @Test
    void listAuthorBooks() {
        var id = UUID.fromString("8bfd9e21-ed93-4719-bea6-7e819b5bab6b");
        var author = repository.findById(id).get();

        List<Book> bookList = bookRepository.findByAuthor(author);
        author.setBooks(bookList);
        author.getBooks().forEach(System.out::println);
    }
}