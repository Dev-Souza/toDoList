package com.project.ToDoList.models.categories;

public record CategoryDTO (
        Long id,
        String nameCategory,
        String descriptionCategory,
        CorCategoryEnum corCategory,
        TipoCategoryEnum tipoCategory,
        Long user_id
){}