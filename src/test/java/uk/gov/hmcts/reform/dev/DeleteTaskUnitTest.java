package uk.gov.hmcts.reform.dev;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.service.TaskServiceImpl;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteTaskUnitTest {
    TaskServiceImpl taskService = new TaskServiceImpl();
    int validTaskId;

    @BeforeEach
    void setUp() {
        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueDateTime(LocalDateTime.now().plusDays(1))
            .build();

        validTaskId = taskService.createTask(taskReq).getId();

    }

    @Test
    void testDeleteTaskSuccess() {
        taskService.deleteTask(validTaskId);
        assertThat(taskService.getTaskById(validTaskId)).isNull();
    }

    @Test
    void testDeleteTaskDoesNotExist() {
        taskService.deleteTask(9999);
        assertThrows(
            TaskNotFoundException.class, () -> {
                taskService.getTaskById(-1);
            });
    }

    @Test
    void testDeleteInvalidTask() {
        assertThrows(
            InvalidParameterException.class, () -> {
                taskService.deleteTask(-1);
            });
    }
}
