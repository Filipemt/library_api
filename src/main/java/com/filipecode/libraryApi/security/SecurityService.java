package com.filipecode.libraryApi.security;

import com.filipecode.libraryApi.model.entities.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    public UserLogin getByLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof CustomAuthentication customAuthentication) {
            return customAuthentication.getUser();
        }

        return null;
    }
}
