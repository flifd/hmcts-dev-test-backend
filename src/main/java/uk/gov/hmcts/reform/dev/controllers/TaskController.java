package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.dtos.UpdateStatusRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.service.TaskServiceImpl;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class TaskController {
    @Autowired
    private TaskServiceImpl service;

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> welcome() {
        return ok("Welcome to the HMCTS task app");
    }

    @GetMapping(value = "/tasks")
    public Iterable getAllTasks() {
        return this.taskRepository.findAll();
    }

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.GET)
    public Optional<Task> getTaskById(@PathVariable int taskId) {
        return this.taskRepository.findById(taskId);
    }

    @PostMapping(value = "/tasks")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return ok(service.createTask(taskRequest));
    }

    @PutMapping(value = "/tasks/{id}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int taskId, @RequestBody UpdateStatusRequest request) {
        service.updateTask(taskId, request.getStatus());
        return ok("Task status updated successfully");
    }

    @DeleteMapping(value = "/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        service.deleteTask(taskId);
        return ok("Task deleted successfully");
    }
}
