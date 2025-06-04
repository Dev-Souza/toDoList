package com.project.ToDoList.controllers.users;

import com.project.ToDoList.models.users.UserRequestDTO;
import com.project.ToDoList.models.users.UserResponseDTO;
import com.project.ToDoList.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    @Autowired
    private UserService userService;

    // CREATE
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user) {return userService.create(user);}

    // GET ALL
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {return userService.getAll();}

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {return userService.getById(id);}

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDTO user){return userService.update(id, user);}

    // DELETE
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUser (@PathVariable("id") Long id){return userService.delete(id);}
}
