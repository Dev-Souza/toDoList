package com.project.ToDoList.controllers.tasks;

import com.project.ToDoList.models.tasks.TaskDTO;
import com.project.ToDoList.services.tasks.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {

    @Autowired
    TaskService taskService;

    // CREATE
    @PostMapping
    ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {return taskService.createTask(taskDTO);}

    // GET ALL
    @GetMapping
    ResponseEntity<List<TaskDTO>> getAllTasks() {return taskService.getAll();}

    // GET BY ID
    @GetMapping("/{id}")
    ResponseEntity<TaskDTO> getTaskByID(@PathVariable("id") Long id) {return taskService.getById(id);}

    // UPDATE
    @PutMapping("/{id}")
    ResponseEntity<TaskDTO> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO task) {return taskService.update(id, task);}

    // DELETE
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {return taskService.delete(id);}

    // GET TASKS BY USER
    @GetMapping("users/{id}")
    ResponseEntity<List<TaskDTO>> getAllTasksByUser(@PathVariable("id") Long idUser) {return taskService.getAllTasksByUserId(idUser);}
}
