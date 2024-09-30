package br.com.kauansouza.todolist.service;

import br.com.kauansouza.todolist.entity.ToDo;
import br.com.kauansouza.todolist.repository.ToDoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ToDoService {
   private ToDoRepository toDoRepository;

    public List<ToDo> create(ToDo toDo) {
      toDoRepository.save(toDo);
      return list();
    }
    public List<ToDo> list() {
       Sort sort = Sort.by("prioridade").descending().and(
               Sort.by("nome").ascending());
      return toDoRepository.findAll(sort);
    }
    public List<ToDo> update(ToDo toDo) {
        toDoRepository.save(toDo);
        return list();
    }
    public List<ToDo> delete(Long id) {
        toDoRepository.deleteById(id);
        return list();
    }
}
