package com.filipecode.libraryApi.model.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String login;

    @Column(name = "senha")
    private String password;

    @Column
    private String email;

    @Type(ListArrayType.class) // Fazer a tradução de List para Array
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
