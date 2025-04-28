package com.filipecode.libraryApi.repositories.specs;

import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.model.enums.BookGender;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genderEqual(BookGender gender) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gender"), gender));
    }
}
