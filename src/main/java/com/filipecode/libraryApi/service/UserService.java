package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.model.entities.User;
import com.filipecode.libraryApi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) {
        var password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
