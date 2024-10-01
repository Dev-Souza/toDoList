package br.com.kauansouza.todolist.controller;

import br.com.kauansouza.todolist.entity.ToDo;
import br.com.kauansouza.todolist.service.ToDoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/toDos")
@AllArgsConstructor
public class ToDoController {

    private ToDoService toDoService;

    @PostMapping()
    List<ToDo> create(@RequestBody @Valid ToDo toDo) {
        return toDoService.create(toDo);
    }

    @GetMapping
    List<ToDo> list() {
        return toDoService.list();
    }

    @PutMapping
    List<ToDo> update(@RequestBody ToDo toDo) {
        return toDoService.update(toDo);
    }

    @DeleteMapping("{id}")
    List<ToDo> delete(@PathVariable("id") Long id) {
        return toDoService.delete(id);
    }
}
