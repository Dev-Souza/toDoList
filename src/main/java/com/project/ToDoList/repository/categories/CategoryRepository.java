package com.project.ToDoList.repository.categories;

import com.project.ToDoList.models.categories.CategoriesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoriesModel, Long> {
}
