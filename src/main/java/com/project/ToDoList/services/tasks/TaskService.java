package com.project.ToDoList.services.tasks;

import com.project.ToDoList.models.categories.CategoriesModel;
import com.project.ToDoList.models.categories.CategoryDTO;
import com.project.ToDoList.models.tasks.StatusTasksEnum;
import com.project.ToDoList.models.tasks.TaskDTO;
import com.project.ToDoList.models.tasks.TasksModel;
import com.project.ToDoList.models.users.UsersModel;
import com.project.ToDoList.repository.categories.CategoryRepository;
import com.project.ToDoList.repository.tasks.TaskRepository;
import com.project.ToDoList.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // CONVERSOR: DTO → ENTITY
    private TasksModel dtoToEntity(TaskDTO dto) {
        TasksModel task = new TasksModel();
        task.setTitleTask(dto.titleTask());
        task.setDescriptionTask(dto.descriptionTask());
        task.setStatusTask(dto.statusTask());
        task.setPriorityTask(dto.priorityTask());
        task.setDateLimit(dto.dateLimit());

        // GET IF USER IS VALID
        Optional<UsersModel> userBuscado = userRepository.findById(dto.user_id());
        userBuscado.ifPresent(task::setUser);

        // GET IF CATEGORY IS VALID
        Optional<CategoriesModel> categoryBuscado = categoryRepository.findById(dto.category_id());
        categoryBuscado.ifPresent(task::setCategory);
        return task;
    }

    // CONVERSOR: ENTITY → DTO
    private TaskDTO entityToDTO(TasksModel task) {
        return new TaskDTO(
                task.getTitleTask(),
                task.getDescriptionTask(),
                task.getStatusTask(),
                task.getPriorityTask(),
                task.getDateLimit(),
                task.getUser().getId(),
                task.getCategory().getId()
        );
    }

    // CREATE TASK
    public ResponseEntity<TaskDTO> createTask(TaskDTO dto) {
        // GET User
        Optional<UsersModel> userBuscado = userRepository.findById(dto.user_id());

        if (userBuscado.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        // GET Category
        Optional<CategoriesModel> categoryBuscado = categoryRepository.findById(dto.category_id());
        if (categoryBuscado.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada.");
        }

        TasksModel task = new TasksModel();
        task.setTitleTask(dto.titleTask());
        task.setDescriptionTask(dto.descriptionTask());
        task.setStatusTask(dto.statusTask());
        task.setPriorityTask(dto.priorityTask());
        task.setDateLimit(dto.dateLimit());
        task.setUser(userBuscado.get());
        task.setCategory(categoryBuscado.get());

        // SAVE IN DATABASE
        taskRepository.save(task);
        return ResponseEntity.ok(entityToDTO(task));
    }

    // GET TASKS
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> tasks = taskRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    // GET BY TASKS
    public ResponseEntity<TaskDTO> getById(Long id) {
        Optional<TasksModel> taskBuscada = taskRepository.findById(id);
        return taskBuscada
                .map(task -> ResponseEntity.ok(entityToDTO(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    public ResponseEntity<TaskDTO> update(Long id, TaskDTO dto) {
        // USER IS PRESENT?
        Optional<UsersModel> userBuscado = userRepository.findById(dto.user_id());
        if (userBuscado.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        // CATEGORY IS PRESENT?
        Optional<CategoriesModel> categoryBuscado = categoryRepository.findById(dto.category_id());
        if (categoryBuscado.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada.");
        }

        // TASK EXISTING
        Optional<TasksModel> taskBuscada = taskRepository.findById(id);
        if (taskBuscada.isPresent()) {
            TasksModel task = taskBuscada.get();
            task.setTitleTask(dto.titleTask());
            task.setDescriptionTask(dto.descriptionTask());
            task.setStatusTask(dto.statusTask());
            task.setPriorityTask(dto.priorityTask());
            task.setDateLimit(dto.dateLimit());
            task.setUser(userBuscado.get());
            task.setCategory(categoryBuscado.get());

            // SAVE IN DATABASE
            TasksModel updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(entityToDTO(updatedTask));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE TASK
    public ResponseEntity<Void> delete(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET ALL TASKS BY ID USER
    public ResponseEntity<List<TaskDTO>> getAllTasksByUserId(Long idUser) {
        Optional<UsersModel> userBuscado = userRepository.findById(idUser);
        // IF IS EMPTY
        if (userBuscado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<TasksModel> listTasks = taskRepository.findAllByUser(userBuscado);
        return ResponseEntity.ok(listTasks
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList()));
    }

    // GET ALL TASKS BY STATUS AND USER
    public ResponseEntity<List<TaskDTO>> getAllTasksByStatusAndUser(StatusTasksEnum status, Long idUser) {
        Optional<UsersModel> userBuscado = userRepository.findById(idUser);
        // IF IS EMPTY
        if (userBuscado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<TasksModel> listTasks = taskRepository.findTasksByStatusAndUser(status, userBuscado);
        return ResponseEntity.ok(listTasks
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList()));
    }
}
