package uk.gov.hmcts.reform.dev.service;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public List<TaskResponse> getAllTasks() {
        return List.of();
    }

    @Override
    public TaskResponse getTaskById(int id) {
        return null;
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
    }
}
