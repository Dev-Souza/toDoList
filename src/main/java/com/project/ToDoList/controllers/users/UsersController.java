package com.project.ToDoList.controllers.users;

import com.project.ToDoList.models.users.UserLoginDTO;
import com.project.ToDoList.models.users.UserRequestDTO;
import com.project.ToDoList.models.users.UserResponseDTO;
import com.project.ToDoList.models.users.UsersModel;
import com.project.ToDoList.services.users.TokenService;
import com.project.ToDoList.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    // CREATE
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@ModelAttribute UserRequestDTO user) {return userService.create(user);}

    // GET ALL
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {return userService.getAll();}

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {return userService.getById(id);}

    // UPDATE
    @PostMapping("/{id}/update")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long id, @ModelAttribute UserRequestDTO user){return userService.update(id, user);}

    // DELETE
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUser (@PathVariable("id") Long id){return userService.delete(id);}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password())
        );

        //GET BY ID
        UserResponseDTO userResponse = userService.getUserByUsername(loginDTO.username());

        String token = tokenService.generateToken((UsersModel) authentication.getPrincipal());
        return ResponseEntity.ok().body(
                Map.of(
                        "token", token,
                        "userId", userResponse.id()
                )
        );
    }
}
