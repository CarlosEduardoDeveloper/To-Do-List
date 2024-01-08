package com.todolist.service;

import com.todolist.exception.DuplicateTaskException;
import com.todolist.exception.TaskNotFoundException;
import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

/*    public Task saveTask(Task task) {
        try {
            taskRepository.findById(task.getId());

            if (taskRepository.existsByDescription(task.getDescription())) {
                throw new DuplicateTaskException("Já existe uma tarefa com esse nome");
            }

            return taskRepository.save(task);
        } catch (EntityExistsException e) {
            throw new DuplicateTaskException(e.getMessage());
        }
    }*/

    public Task saveTask(Task task) {
        try {
            // Verifica se a tarefa já existe pelo ID
            if (task.getId() != null && taskRepository.existsById(task.getId())) {
                throw new DuplicateTaskException("Já existe uma tarefa com esse ID");
            }

            // Verifica se a tarefa já existe pela descrição
            if (taskRepository.existsByDescription(task.getDescription())) {
                throw new DuplicateTaskException("Já existe uma tarefa com essa descrição");
            }

            return taskRepository.save(task);
        } catch (DuplicateTaskException e) {
            // Propaga a exceção específica para tratamento adequado
            throw e;
        } catch (Exception ex) {
            // Trata outras exceções genéricas se necessário
            throw new RuntimeException("Erro ao salvar a tarefa", ex);
        }
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
