package com.filipecode.libraryApi.repositories;

import com.filipecode.libraryApi.model.entities.Author;
import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.model.enums.BookGender;
import com.filipecode.libraryApi.repositories.AuthorRepository;
import com.filipecode.libraryApi.repositories.BookRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void saveTest() {

        Book book = new Book();

        book.setIsbn("24569875632145");
        book.setTitle("Entendendo Algoritmos");
        book.setPublicationDate(LocalDate.of(1947, 6, 25));
        book.setGender(BookGender.CIENCIA);
        book.setPrice(BigDecimal.valueOf(35));

        Author author = authorRepository
                .findById(UUID.fromString("ce2cc67b-d0f5-454b-8a99-773204edfa50"))
                .orElse(null);

        book.setAuthor(new Author());

        bookRepository.save(book);
    }

    @Test
    public void saveAuthorAndBookTest() {

        Book book = new Book();

        book.setIsbn("5546845123435");
        book.setTitle("Clean Code");
        book.setPublicationDate(LocalDate.of(1947, 6, 25));
        book.setGender(BookGender.CIENCIA);
        book.setPrice(BigDecimal.valueOf(250));

        Author author = new Author();

        author.setName("Martin");
        author.setNationality("Chinese");
        author.setDateOfBirth(LocalDate.of(1929, 6, 12));

        authorRepository.save(author);

        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    public void saveCascadeTest() {

        Book book = new Book();

        book.setIsbn("24569875632145");
        book.setTitle("Entendendo Algoritmos");
        book.setPublicationDate(LocalDate.of(1947, 6, 25));
        book.setGender(BookGender.CIENCIA);
        book.setPrice(BigDecimal.valueOf(35));

        Author author = new Author();

        author.setName("Aditya Barghava");
        author.setNationality("British");
        author.setDateOfBirth(LocalDate.of(1929, 6, 12));

        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    public void updateAuthorBookTest() {
        UUID bookId = UUID.fromString("e8bdec62-5b31-4924-83ec-b81731ac2516");
        var updateBook = bookRepository.findById(bookId)
                .orElse(null);

        UUID idAuthor = UUID.fromString("2f4e16af-f7df-4ca2-a8e6-28c4f008444d");
        Author author = authorRepository.findById(idAuthor)
                .orElse(null);

        updateBook.setAuthor(author); // may produce 'NullPointerException'

        bookRepository.save(updateBook);
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("3e79b88f-b20a-466f-985a-a9008f4c397f");

        bookRepository.deleteById(id);
    }

    @Test
    public void listBooksTest() {
        List<Book> bookLisk = bookRepository.findAll();
        bookLisk.forEach(System.out::println);
    }

    @Test
    @Transactional
    public void findBookWithAuthor() {
        var id = UUID.fromString("cb75fcd1-8d5c-4905-a624-2cef97217ca1");

        Book book = bookRepository.findById(id).orElse(null);

        System.out.print("Book: ");
        System.out.println(book.getTitle());

        System.out.print("Author: ");
        System.out.println(book.getAuthor().getName());
    }

    @Test
    void findByTitle() {
        List<Book> list = bookRepository.findByTitle("Entendendo Algoritmos");
        list.forEach(System.out::println);
    }
}
