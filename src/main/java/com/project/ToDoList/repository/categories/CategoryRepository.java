package com.project.ToDoList.repository.categories;

import com.project.ToDoList.models.categories.CategoriesModel;
import com.project.ToDoList.models.categories.CorCategoryEnum;
import com.project.ToDoList.models.users.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoriesModel, Long> {

    @Query("SELECT c FROM CategoriesModel c WHERE c.user = :user")
    List<CategoriesModel> findAllByUser(@Param("user") Optional<UsersModel> user);

    @Query("SELECT c.corCategoryEnum FROM CategoriesModel c WHERE c.id = :id")
    CorCategoryEnum getColorCategoryById(@Param("id") Long id);
}
