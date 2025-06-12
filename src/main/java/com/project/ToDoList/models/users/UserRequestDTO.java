package com.project.ToDoList.models.users;

import org.springframework.web.multipart.MultipartFile;

public record UserRequestDTO (
    String username,
    String password,
    String email,
    String phone,
    MultipartFile fotoPerfil,
    UsersRole role
){}