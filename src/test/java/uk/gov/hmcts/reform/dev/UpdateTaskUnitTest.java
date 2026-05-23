package uk.gov.hmcts.reform.dev;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.service.TaskServiceImpl;
import uk.gov.hmcts.reform.dev.utils.Constants;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateTaskUnitTest {

    @Test
    void testUpdateSuccess() {
        TaskServiceImpl taskService = new TaskServiceImpl();
        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueDateTime(LocalDateTime.now().plusDays(1))
            .build();

        TaskResponse response = taskService.createTask(taskReq);
        response = taskService.updateTask(response.getId(), "IN_PROGRESS");

        assertThat(response.getStatus()).isEqualTo(Constants.Status.IN_PROGRESS);
    }

    @Test
    void testInvalidStatus() {
        TaskServiceImpl taskService = new TaskServiceImpl();
        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueDateTime(LocalDateTime.now().plusDays(1))
            .build();

        TaskResponse response = taskService.createTask(taskReq);
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.updateTask(response.getId(), "REJECTED");
        });
    }
}
