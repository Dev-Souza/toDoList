package br.com.kauansouza.todolist.repository;

import br.com.kauansouza.todolist.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
