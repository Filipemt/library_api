package com.filipecode.libraryApi.controller;

import com.filipecode.libraryApi.controller.mappers.UserLoginMapper;
import com.filipecode.libraryApi.model.dtos.request.UserLoginDTO;
import com.filipecode.libraryApi.service.UserLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;
    private final UserLoginMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid UserLoginDTO dto) {
        var user = mapper.toEntity(dto);
        userLoginService.save(user);
    }
}
