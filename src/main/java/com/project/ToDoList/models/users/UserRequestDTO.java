package com.project.ToDoList.models.users;

public record UserRequestDTO (
    String userName,
    String password,
    String email,
    String phone,
    String fotoPerfil,
    UsersRole role
){}