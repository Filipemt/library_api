package com.filipecode.libraryApi.model.entities;

import com.filipecode.libraryApi.model.enums.BookGender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "author")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String title;

    @Column(name = "data_publicacao")
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private BookGender gender;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne(
//             cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_autor")
    private Author author;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UserLogin userLogin;

}