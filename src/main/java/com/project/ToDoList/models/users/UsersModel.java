package com.project.ToDoList.models.users;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();



}
