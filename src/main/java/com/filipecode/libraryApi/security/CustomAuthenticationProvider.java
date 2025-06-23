package com.filipecode.libraryApi.security;

import com.filipecode.libraryApi.model.entities.UserLogin;
import com.filipecode.libraryApi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String typePassword = authentication.getCredentials().toString();

        UserLogin foundUser = userService.getByLogin(login);

        if (foundUser == null) {
            getErrorUserNotFound();
        }
        String cryptedPassword = foundUser.getPassword();
        
        boolean matchesPassword = passwordEncoder.matches(typePassword, cryptedPassword);

        if (matchesPassword) {
            return new CustomAuthentication(foundUser);
        }

        throw getErrorUserNotFound();
    }

    private UsernameNotFoundException getErrorUserNotFound() {
        throw new UsernameNotFoundException("Incorrect user and/or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
