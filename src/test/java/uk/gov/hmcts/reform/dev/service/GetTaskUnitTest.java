package uk.gov.hmcts.reform.dev.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskMapper;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.utils.Constants;
import uk.gov.hmcts.reform.dev.utils.Utils;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTaskUnitTest {
    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @Mock
    Utils utils;

    @InjectMocks
    TaskServiceImpl taskService;

    Task task = new Task(
        0,
        "Review blocked case",
        "Investigate the cause",
        Constants.Status.PENDING,
        LocalDateTime.parse("2026-05-25T00:00:00"),
        LocalDateTime.parse("2026-05-22T00:00:00"),
        LocalDateTime.parse("2026-05-22T00:00:00")
    );

    TaskResponse expectedTaskRes = TaskResponse.builder()
        .id(0)
        .title("Review blocked case")
        .description("Investigate the cause")
        .status(Constants.Status.PENDING)
        .dueTimestamp(LocalDateTime.parse("2026-05-25T00:00:00"))
        .createdTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
        .updatedTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
        .build();

    @Test
    void testGetTask() {
        when(utils.validateTaskId(0)).thenReturn(true);
        when(taskMapper.toDto(task)).thenReturn(expectedTaskRes);
        when(taskRepository.findById(0)).thenReturn(java.util.Optional.of(task));

        TaskResponse response = taskService.getTaskById(0);
        assertThat(response).isEqualTo(expectedTaskRes);
    }

    @Test
    void testGetAllTasksEmpty() {
        List<Task> emptyTaskList = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(emptyTaskList);

        List<TaskResponse> response = taskService.getAllTasks();
        assertThat(response).isEqualTo(List.of());
    }

    @Test
    void testGetAllTasks() {
        Task task2 = task;
        task2.setId(1);
        TaskResponse expectedTaskRes2 = expectedTaskRes;
        expectedTaskRes2.setId(1);

        List<Task> taskList = List.of(
            task,
            task2
        );
        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskMapper.toDto(task)).thenReturn(expectedTaskRes);
        when(taskMapper.toDto(task2)).thenReturn(expectedTaskRes2);

        List<TaskResponse> response = taskService.getAllTasks();
        assertThat(response.size()).isEqualTo(taskList.size());
    }

    @Test
    void testGetNonExistentTask() {
        when(utils.validateTaskId(9999)).thenReturn(true);
        when(taskRepository.findById(9999)).thenReturn(java.util.Optional.empty());
        TaskResponse response = taskService.getTaskById(9999);
        assertThat(response).isNull();
    }

    @Test
    void testGetInvalidTask() {
        when(utils.validateTaskId(-1)).thenReturn(false);
        assertThrows(
            InvalidParameterException.class, () -> {
                taskService.getTaskById(-1);
            });
    }
}
