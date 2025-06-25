package com.filipecode.libraryApi.service;

import com.filipecode.libraryApi.model.entities.UserLogin;
import com.filipecode.libraryApi.repositories.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserLogin userLogin) {
        var password = userLogin.getPassword();
        userLogin.setPassword(passwordEncoder.encode(password));

        userLoginRepository.save(userLogin);
    }

    public UserLogin getByLogin(String login) {
        return userLoginRepository.findByLogin(login);
    }

    public UserLogin getByEmail(String email) {
        return userLoginRepository.findByEmail(email);
    }
}