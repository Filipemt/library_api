package com.filipecode.libraryApi.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString(exclude = "books")
@EntityListeners(AuditingEntityListener.class) // Classe fica escutando sempre que eu faço uma alteração nessa entidade
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String name;

    @Column(name = "data_nascimento")
    private LocalDate dateOfBirth;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nationality;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY
//    cascade = CascadeType.ALL,
    )
    private List<Book> books;

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
