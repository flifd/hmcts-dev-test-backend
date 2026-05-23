package uk.gov.hmcts.reform.dev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.utils.Constants;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTaskUnitTest {
    @Mock
    TaskRepository taskRepository;

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
        when(taskRepository.findById(0)).thenReturn(java.util.Optional.of(task));

        TaskResponse response = taskService.getTaskById(0);
        assertThat(response).isEqualTo(expectedTaskRes);
    }

    @Test
    void testGetNonExistentTask() {
        when(taskRepository.findById(9999)).thenReturn(java.util.Optional.empty());
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
