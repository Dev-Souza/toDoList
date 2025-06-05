package com.project.ToDoList.models.users;

public record UserResponseDTO(
        Long id,
        String userName,
        String email,
        String phone,
        String fotoPerfil,
        UsersRole role
) {
}