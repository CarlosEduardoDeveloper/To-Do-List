package com.todolist.service;

import com.todolist.exception.DuplicateTaskException;
import com.todolist.exception.TaskNotFoundException;
import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id)));
    }

    public Task saveTask(Task task) {
        try {
            taskRepository.findById(task.getId());

            if (taskRepository.existsByDescription(task.getDescription())) {
                throw new DuplicateTaskException("Já existe uma tarefa com esse nome");
            }

            return taskRepository.save(task);
        } catch (EntityExistsException e) {
            throw new DuplicateTaskException(e.getMessage());
        }
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
