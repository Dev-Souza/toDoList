package com.project.ToDoList.repository.users;

import com.project.ToDoList.models.users.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersModel, Long> {
}