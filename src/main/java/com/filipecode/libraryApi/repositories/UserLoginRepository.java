package com.filipecode.libraryApi.repositories;

import com.filipecode.libraryApi.model.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLoginRepository extends JpaRepository<UserLogin, UUID> {

    UserLogin findByLogin(String login);
}
