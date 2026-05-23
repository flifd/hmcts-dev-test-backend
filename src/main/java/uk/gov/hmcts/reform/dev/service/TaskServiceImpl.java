package uk.gov.hmcts.reform.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskMapper;
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

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> taskDtos = new ArrayList<>();

        Iterable<Task> dbResponse = taskRepository.findAll();
        dbResponse.forEach(task -> {
            taskDtos.add(taskMapper.toDto(task));
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

        return taskMapper.toDto(dbResponse);
    }

    @Override
    public TaskResponse createTask(TaskRequest taskDto) {
        Task task = taskMapper.fromDto(taskDto);

        Task dbResponse = taskRepository.save(task);

        return taskMapper.toDto(dbResponse);
    }

    @Override
    public TaskResponse updateTask(int taskId, String status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));


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
