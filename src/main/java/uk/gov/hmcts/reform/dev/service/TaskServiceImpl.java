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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    public TaskRepository taskRepository;

    @Autowired
    private Utils utils;

    @Override
    public Map<Constants.Status, List<TaskResponse>> getAllTasks() {
        Map<Constants.Status, List<TaskResponse>> taskDtos = new HashMap<>();

        Iterable<Task> dbResponse = taskRepository.findAll();

        dbResponse.forEach(task -> {
            TaskResponse taskDto = TaskMapper.INSTANCE.toDto(task);
            List<TaskResponse> taskList = taskDtos.getOrDefault(taskDto.getStatus(), new ArrayList<>());
            taskList.add(taskDto);
            taskDtos.put(taskDto.getStatus(), taskList);


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

        return TaskMapper.INSTANCE.toDto(dbResponse);
    }

    @Override
    public TaskResponse createTask(TaskRequest taskDto) {
        taskDto.setStatus(Constants.Status.PENDING.name());
        taskDto.setCreatedTimestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        taskDto.setUpdatedTimestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Task task = TaskMapper.INSTANCE.fromDto(taskDto);

        Task dbResponse = taskRepository.save(task);

        return TaskMapper.INSTANCE.toDto(dbResponse);
    }

    @Override
    public TaskResponse updateTask(int taskId, String status) {
        Task dbResponse = taskRepository.updateTask(taskId, Constants.Status.valueOf(status))
            .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        return TaskMapper.INSTANCE.toDto(dbResponse);
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
