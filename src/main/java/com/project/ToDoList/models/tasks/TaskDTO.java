package com.project.ToDoList.models.tasks;

import java.time.LocalDate;

public record TaskDTO(
        Long id,
        String titleTask,
        String descriptionTask,
        StatusTasksEnum statusTask,
        PriorityTasksEnum priorityTask,
        LocalDate dateLimit,
        Long user_id,
        Long category_id
) {
}
