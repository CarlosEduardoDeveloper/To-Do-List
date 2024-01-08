package com.todolist.controller;

import com.todolist.exception.TaskNotFoundException;
import com.todolist.model.Task;
import com.todolist.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findtTaskById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);

        return taskOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> saveTask(@Valid @RequestBody Task task, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Erro de validação: " + Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Optional<Task> existingTask = taskService.findById(id);

            if (existingTask.isPresent()) {
                Task taskToUpdate = existingTask.get();
                taskToUpdate.setDescription(task.getDescription());
                taskToUpdate.setCategory(task.getCategory());
                taskToUpdate.setCompleted(task.isCompleted());

                Task savedTask = taskService.saveTask(taskToUpdate);
                return ResponseEntity.ok(savedTask);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (TaskNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteById(id);
    }
}
