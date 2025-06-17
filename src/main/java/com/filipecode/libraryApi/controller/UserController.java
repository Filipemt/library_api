package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.UserMapper;
import com.filipecode.libraryApi.model.dtos.request.UserDTO;
import com.filipecode.libraryApi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UserDTO dto) {
        var user = mapper.toEntity(dto);
        userService.save(user);
    }
}
