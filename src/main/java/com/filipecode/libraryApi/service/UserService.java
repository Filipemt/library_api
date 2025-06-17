package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.model.entities.UserLogin;
import com.filipecode.libraryApi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserLogin userLogin) {
        var password = userLogin.getPassword();
        userLogin.setPassword(passwordEncoder.encode(password));

        userRepository.save(userLogin);
    }

    public UserLogin getByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}