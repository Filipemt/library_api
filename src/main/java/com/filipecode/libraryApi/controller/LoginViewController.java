package com.filipecode.libraryApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Para páginas web
public class LoginViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
