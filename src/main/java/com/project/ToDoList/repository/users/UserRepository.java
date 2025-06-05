package com.project.ToDoList.repository.users;

import com.project.ToDoList.models.users.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UsersModel, Long> {
    UserDetails findByUsername(String username);
}