package com.filipecode.libraryApi.repositories.specs;

import com.filipecode.libraryApi.model.entities.Book;
import com.filipecode.libraryApi.model.enums.BookGender;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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

    public static Specification<Book> publicationDateEqual(Integer publicationDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.function("to_char", String.class,
                        root.get("publicationDate"), criteriaBuilder.literal("YYYY")), publicationDate.toString());
    }

    public static Specification<Book> authorNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> joinAuthor = root.join("author", JoinType.LEFT);

            return criteriaBuilder.like(criteriaBuilder.upper(joinAuthor.get("name")), "%" + name.toUpperCase() + "%");

            // return criteriaBuilder.like(criteriaBuilder.upper(root.get("author").get("name")), "%" + name.toUpperCase() + "%");
        };
    }
}
