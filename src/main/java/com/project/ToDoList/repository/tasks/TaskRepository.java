package com.project.ToDoList.repository.tasks;

import com.project.ToDoList.models.tasks.StatusTasksEnum;
import com.project.ToDoList.models.tasks.TasksModel;
import com.project.ToDoList.models.users.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TasksModel, Long> {
    @Query("SELECT c FROM TasksModel c WHERE c.user = :user")
    List<TasksModel> findAllByUser(@Param("user") Optional<UsersModel> user);

    @Query("SELECT t FROM TasksModel t WHERE t.statusTask = :status AND t.user = :user")
    List<TasksModel> findTasksByStatusAndUser(@Param("status") StatusTasksEnum status, @Param("user") Optional<UsersModel> user);
}
