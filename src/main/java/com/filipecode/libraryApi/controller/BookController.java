package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
}
