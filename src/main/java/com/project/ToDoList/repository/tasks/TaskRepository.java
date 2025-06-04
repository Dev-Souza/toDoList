package com.project.ToDoList.repository.tasks;

import com.project.ToDoList.models.tasks.TasksModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TasksModel, Long> {
}
