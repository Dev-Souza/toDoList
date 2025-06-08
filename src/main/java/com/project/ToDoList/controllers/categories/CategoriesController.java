package com.project.ToDoList.controllers.categories;

import com.project.ToDoList.models.categories.CategoryDTO;
import com.project.ToDoList.services.categories.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {

    @Autowired
    CategoryService categoryService;

    // CREATE
    @PostMapping
    ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {return categoryService.create(categoryDTO);}

    // GET ALL
    @GetMapping
    ResponseEntity<List<CategoryDTO>> getAllCategories() {return categoryService.findAll();}

    // GET BY ID
    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {return categoryService.getById(id);}

    // UPDATE
    @PutMapping("/{id}")
    ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO category) {return categoryService.update(id, category);}

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {return categoryService.delete(id);}

    @GetMapping("users/{id}")
    ResponseEntity<List<CategoryDTO>> getCategoriesByIdUser(@PathVariable("id") Long idUser){return categoryService.getCategoryByIdUser(idUser);}
}
