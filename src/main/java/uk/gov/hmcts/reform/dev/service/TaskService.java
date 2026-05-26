package uk.gov.hmcts.reform.dev.service;

import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.utils.Constants;

import java.util.List;
import java.util.Map;

public interface TaskService {
    Map<Constants.Status, List<TaskResponse>> getAllTasks();

    TaskResponse getTaskById(int id);

    TaskResponse createTask(TaskRequest task);

    TaskResponse updateTask(int taskId, String status);

    void deleteTask (int taskId);
}
