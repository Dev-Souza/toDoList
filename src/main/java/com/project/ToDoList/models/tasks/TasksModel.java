package com.project.ToDoList.models.tasks;

import com.project.ToDoList.models.categories.CategoriesModel;
import com.project.ToDoList.models.users.UsersModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "tasks")
@Setter
@Getter
@Entity
public class TasksModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titleTask;
    @Column(nullable = false)
    private String descriptionTask;
    @Enumerated(EnumType.STRING)
    private StatusTasksEnum statusTask;
    @Enumerated(EnumType.STRING)
    private PriorityTasksEnum priorityTask;
    @Column(nullable = false)
    private LocalDate dateLimit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersModel user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoriesModel category;
}