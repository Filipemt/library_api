package com.filipecode.libraryApi.repositories;

import com.filipecode.libraryApi.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByLogin(String login);
}
