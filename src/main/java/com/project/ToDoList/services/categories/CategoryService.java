package com.project.ToDoList.services.categories;

import com.project.ToDoList.models.categories.CategoriesModel;
import com.project.ToDoList.models.categories.CategoryDTO;
import com.project.ToDoList.models.users.UsersModel;
import com.project.ToDoList.repository.categories.CategoryRepository;
import com.project.ToDoList.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // CONVERSOR: DTO → ENTITY
    private CategoriesModel dtoToEntity(CategoryDTO dto) {
        CategoriesModel category = new CategoriesModel();
        category.setNameCategory(dto.nameCategory());
        category.setDescriptionCategory(dto.descriptionCategory());
        category.setCorCategoryEnum(dto.corCategory());
        category.setTipoCategoryEnum(dto.tipoCategory());

        // Buscar se o user é valido
        Optional<UsersModel> userBuscado = userRepository.findById(dto.user_id());
        userBuscado.ifPresent(category::setUser);
        return category;
    }

    // CONVERSOR: ENTITY → DTO
    private CategoryDTO entityToDTO(CategoriesModel category) {
        return new CategoryDTO(
                category.getId(),
                category.getNameCategory(),
                category.getDescriptionCategory(),
                category.getCorCategoryEnum(),
                category.getTipoCategoryEnum(),
                category.getUser().getId()
        );
    }

    // CREATE CATEGORY
    public ResponseEntity<CategoryDTO> create(CategoryDTO categoryDTO) {
        // GET User
        Optional<UsersModel> userBuscado = userRepository.findById(categoryDTO.user_id());

        if (userBuscado.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        CategoriesModel categoryEntity = new CategoriesModel();
        categoryEntity.setNameCategory(categoryDTO.nameCategory());
        categoryEntity.setDescriptionCategory(categoryDTO.descriptionCategory());
        categoryEntity.setCorCategoryEnum(categoryDTO.corCategory());
        categoryEntity.setTipoCategoryEnum(categoryDTO.tipoCategory());
        categoryEntity.setUser(userBuscado.get());

        // Salvar no banco
        categoryRepository.save(categoryEntity);
        return ResponseEntity.ok(entityToDTO(categoryEntity));
    }

    // GET CATEGORIES
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categoriesDTO = categoryRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriesDTO);
    }

    // GET BY CATEGORY
    public ResponseEntity<CategoryDTO> getById(Long id) {
        Optional<CategoriesModel> categoryBuscada = categoryRepository.findById(id);
        return categoryBuscada
                .map(category -> ResponseEntity.ok(entityToDTO(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE CATEGORY
    public ResponseEntity<CategoryDTO> update(Long id, CategoryDTO categoryDTO) {
        // USER IS PRESENT?
        Optional<UsersModel> userBuscado = userRepository.findById(categoryDTO.user_id());

        if (userBuscado.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        Optional<CategoriesModel> categoryBuscada = categoryRepository.findById(id);
        if (categoryBuscada.isPresent()) {
            CategoriesModel categoryEntity = categoryBuscada.get();
            categoryEntity.setNameCategory(categoryDTO.nameCategory());
            categoryEntity.setDescriptionCategory(categoryDTO.descriptionCategory());
            categoryEntity.setCorCategoryEnum(categoryDTO.corCategory());
            categoryEntity.setTipoCategoryEnum(categoryDTO.tipoCategory());
            categoryEntity.setUser(userBuscado.get());

            CategoriesModel updatedCategoryEntity = categoryRepository.save(categoryEntity);
            return ResponseEntity.ok(entityToDTO(updatedCategoryEntity));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE CATEGORY
    public ResponseEntity<Void> delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET CATEGORY BY ID USER
    public ResponseEntity<List<CategoryDTO>> getCategoryByIdUser(Long idUser) {
        // GET USER BY ID
        Optional<UsersModel> usersModel = userRepository.findById(idUser);
        // User IS PRESENT?
        if (usersModel.isPresent()) {
            List<CategoriesModel> categoryByIdUser = categoryRepository.findAllByUser(usersModel);
            return ResponseEntity.ok(categoryByIdUser
                    .stream()
                    .map(this::entityToDTO)
                    .collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }
}