package uk.gov.hmcts.reform.dev.service;

import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(int id);

    TaskResponse createTask(TaskRequest task);

    TaskResponse updateTask(int taskId, String status);

    void deleteTask (int taskId);
}
