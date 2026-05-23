package uk.gov.hmcts.reform.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.utils.Constants;
import uk.gov.hmcts.reform.dev.utils.Utils;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    public TaskRepository taskRepository;

    @Autowired
    private Utils utils;

    @Override
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> taskDtos = new ArrayList<>();

        Iterable<Task> tasks = taskRepository.findAll();
        tasks.forEach(task -> {
            taskDtos.add(TaskResponse.builder()
                             .id(task.getId())
                             .title(task.getTitle())
                             .description(task.getDescription())
                             .status(Constants.Status.valueOf(task.getStatus().name()))
                             .dueTimestamp(task.getDueTimestamp())
                             .createdTimestamp(task.getCreatedTimestamp())
                             .updatedTimestamp(task.getUpdatedTimestamp())
                             .build());
        });

        return taskDtos;
    }

    @Override
    public TaskResponse getTaskById(int taskId) {
        if (!utils.validateTaskId(taskId)) {
            throw new InvalidParameterException("Invalid task ID");
        }

        Task dbResponse = taskRepository.findById(taskId).orElse(null);

        if (dbResponse == null) {
            return null;
        }

        return TaskResponse.builder()
            .id(dbResponse.getId())
            .title(dbResponse.getTitle())
            .description(dbResponse.getDescription())
            .status(Constants.Status.valueOf(dbResponse.getStatus().name()))
            .dueTimestamp(dbResponse.getDueTimestamp())
            .createdTimestamp(dbResponse.getCreatedTimestamp())
            .updatedTimestamp(dbResponse.getUpdatedTimestamp())
            .build();
    }

    @Override
    public TaskResponse createTask(TaskRequest task) {
        return new TaskResponse();
    }

    @Override
    public TaskResponse updateTask(int taskId, String status) {
        return new TaskResponse();
    }

    @Override
    public void deleteTask(int taskId) {
        if (!utils.validateTaskId(taskId)) {
            throw new InvalidParameterException("Invalid task ID");
        }

        // Check if the task exists first
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        taskRepository.deleteById(taskId);
    }
}
