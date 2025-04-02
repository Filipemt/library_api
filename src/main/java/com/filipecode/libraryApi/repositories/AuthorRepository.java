package com.filipecode.libraryApi.repositories;

import com.filipecode.libraryApi.model.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
