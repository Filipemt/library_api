package com.filipecode.libraryApi.security;

import com.filipecode.libraryApi.model.entities.UserLogin;
import com.filipecode.libraryApi.service.UserLoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String PATTERN_PASSWORD = "123";

    private final UserLoginService service;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        UserLogin userLogin = service.getByEmail(email);

        userLogin = registerIfNotExists(userLogin, email);

        authentication = new CustomAuthentication(userLogin);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private UserLogin registerIfNotExists(UserLogin userLogin, String email) {
        if (userLogin == null) {
            userLogin = new UserLogin();

            userLogin.setEmail(email);
            userLogin.setLogin(getLoginByEmail(email));
            userLogin.setPassword(PATTERN_PASSWORD);
            userLogin.setRoles(List.of("OPERADOR"));

            service.save(userLogin);
        }
        return userLogin;
    }

    private String getLoginByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
