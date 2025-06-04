package com.project.ToDoList.controllers.users;

import com.project.ToDoList.models.users.UsersModel;
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
    public ResponseEntity<UsersModel> createUser(@RequestBody UsersModel user) {return userService.create(user);}

    // GET ALL
    @GetMapping
    public ResponseEntity<List<UsersModel>> getAllUsers() {return userService.getAll();}

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UsersModel> getUserById(@PathVariable("id") Long id) {return userService.getById(id);}

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UsersModel> updateUser(@PathVariable("id") Long id, @RequestBody UsersModel user){return userService.update(id, user);}

    // DELETE
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUser (@PathVariable("id") Long id){return userService.delete(id);}
}
