package com.project.ToDoList.models.categories;

import com.project.ToDoList.models.tasks.TasksModel;
import com.project.ToDoList.models.users.UsersModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "categories")
@Setter
@Getter
@Entity
public class CategoriesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nameCategory;
    @Column(nullable = false)
    private String descriptionCategory;
    @Enumerated(EnumType.STRING)
    private CorCategoryEnum corCategoryEnum;
    @Enumerated(EnumType.STRING)
    private TipoCategoryEnum tipoCategoryEnum;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TasksModel> tasks = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersModel user_id;
}
