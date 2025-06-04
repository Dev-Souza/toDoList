package com.project.ToDoList.models.tasks;

import java.time.LocalDate;

public record TaskDTO(
    String titleTask,
    String descriptionTask,
    StatusTasksEnum statusTask,
    PriorityTasksEnum priorityTask,
    LocalDate dateLimit,
    Long user_id,
    Long category_id
) { }
