package com.filipecode.libraryApi.model.dtos.request;

import java.util.List;

public record UserDTO(String login, String password, List<String> roles) {
}
