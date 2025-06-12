package com.project.ToDoList.services.users;

import com.project.ToDoList.models.users.UserRequestDTO;
import com.project.ToDoList.models.users.UserResponseDTO;
import com.project.ToDoList.models.users.UsersModel;
import com.project.ToDoList.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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

    // Tratativa de file
    @Autowired
    private FileStorageService fileStorageService;

    // CONVERSOR: DTO → ENTITY
    private UsersModel dtoToEntity(UserRequestDTO dto) {
        UsersModel user = new UsersModel();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
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
        // TRY PHOTO USER
        // Se uma foto de perfil foi enviada, guarde-a
        if (userDTO.fotoPerfil() != null && !userDTO.fotoPerfil().isEmpty()) {
            String filename = fileStorageService.save(userDTO.fotoPerfil());
            // Guarde o nome do ficheiro (ou o caminho completo) na sua entidade
            userEntity.setFotoPerfil(filename);
        }
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

            // Atualiza os campos simples
            user.setUsername(updatedUserDTO.username());
            user.setEmail(updatedUserDTO.email());
            user.setPhone(updatedUserDTO.phone());
            user.setRole(updatedUserDTO.role()); // Supondo que role pode ser atualizado

            // Se uma nova senha foi fornecida, codifique-a
            if (updatedUserDTO.password() != null && !updatedUserDTO.password().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUserDTO.password()));
            }

            if (updatedUserDTO.fotoPerfil() != null && !updatedUserDTO.fotoPerfil().isEmpty()) {
                String filename = fileStorageService.save(updatedUserDTO.fotoPerfil());
                // Guarde o nome do ficheiro (ou o caminho completo) na sua entidade
                user.setFotoPerfil(filename);
            }

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

    // GET USER BY USERNAME
    public UserResponseDTO getUserByUsername(String username) {
        Optional<UsersModel> userOpt = userRepository.findByUsernameIgnoreCase(username);

        return userOpt
                .map(this::entityToResponseDTO)
                .orElse(null);
    }
}