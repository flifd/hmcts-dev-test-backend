package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.service.TaskServiceImpl;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetTaskUnitTest {
    TaskServiceImpl taskService = new TaskServiceImpl();
    int validTaskId;
    TaskResponse validTask;

    @BeforeEach
    void setUp() {
        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueDateTime(LocalDateTime.now().plusDays(1))
            .build();

        validTask = taskService.createTask(taskReq);
        validTaskId = validTask.getId();

    }

    @Test
    void testGetTask() {
        TaskResponse response = taskService.getTaskById(validTaskId);
        assertThat(response).isEqualTo(validTask);
    }

    @Test
    void testGetNonExistentTask() {
        TaskResponse response = taskService.getTaskById(9999);
        assertThat(response).isNull();
    }

    @Test
    void testGetInvalidTask() {
        assertThrows(
            InvalidParameterException.class, () -> {
                taskService.getTaskById(-1);
            });
    }
}
