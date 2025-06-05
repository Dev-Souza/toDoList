package com.project.ToDoList.services.users;

import com.project.ToDoList.models.users.UserRequestDTO;
import com.project.ToDoList.models.users.UserResponseDTO;
import com.project.ToDoList.models.users.UsersModel;
import com.project.ToDoList.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CONVERSOR: DTO → ENTITY
    private UsersModel dtoToEntity(UserRequestDTO dto) {
        UsersModel user = new UsersModel();
        user.setUsername(dto.userName());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setFotoPerfil(dto.fotoPerfil());
        user.setRole(dto.role());
        return user;
    }

    // CONVERSOR: ENTITY → DTO
    private UserResponseDTO entityToResponseDTO(UsersModel user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getFotoPerfil(),
                user.getRole()
        );
    }

    // CREATE USER
    public ResponseEntity<UserResponseDTO> create(UserRequestDTO userDTO) {
        UsersModel userEntity = dtoToEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.password()));
        UsersModel savedUser = userRepository.save(userEntity);
        return ResponseEntity.status(201).body(entityToResponseDTO(savedUser));
    }

    // GET ALL USERS
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> usersDTO = userRepository.findAll()
                .stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usersDTO);
    }

    // GET USER BY ID
    public ResponseEntity<UserResponseDTO> getById(Long id) {
        Optional<UsersModel> userOpt = userRepository.findById(id);
        return userOpt
                .map(user -> ResponseEntity.ok(entityToResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE USER
    public ResponseEntity<UserResponseDTO> update(Long id, UserRequestDTO updatedUserDTO) {
        Optional<UsersModel> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            UsersModel user = existingUserOpt.get();
            user.setUsername(updatedUserDTO.userName());
            user.setPassword(updatedUserDTO.password());
            user.setEmail(updatedUserDTO.email());
            user.setPhone(updatedUserDTO.phone());
            user.setFotoPerfil(updatedUserDTO.fotoPerfil());

            UsersModel savedUser = userRepository.save(user);
            return ResponseEntity.ok(entityToResponseDTO(savedUser));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE USER
    public ResponseEntity<Void> delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}