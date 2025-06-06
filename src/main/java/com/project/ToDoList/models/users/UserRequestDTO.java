package com.project.ToDoList.models.users;

public record UserRequestDTO (
    String username,
    String password,
    String email,
    String phone,
    String fotoPerfil,
    UsersRole role
){}