package com.project.ToDoList.services.users;

import com.project.ToDoList.models.users.UsersModel;
import com.project.ToDoList.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // CREATE USER
    public ResponseEntity<UsersModel> create (UsersModel user){
        UsersModel newUser = userRepository.save(user);
        return ResponseEntity.status(201).body(newUser);
    }

    // GET USER
    public ResponseEntity<List<UsersModel>> getAll(){
        List<UsersModel> listUsers = userRepository.findAll();
        return ResponseEntity.ok(listUsers);
    }

    // GET BY ID USER
    public ResponseEntity<UsersModel> getById (Long id){
        Optional<UsersModel> findUser = userRepository.findById(id);
        return findUser.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE USER
    public ResponseEntity<UsersModel> update(Long id, UsersModel updatedUser){
        Optional<UsersModel> existing = userRepository.findById(id);
        if(existing.isPresent()){
            UsersModel user = existing.get();
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setFotoPerfil(updatedUser.getFotoPerfil());
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE USER
    public ResponseEntity<Void> delete (Long id){
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}