package uk.gov.hmcts.reform.dev.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.dtos.UpdateStatusRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskMapper;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.utils.Constants;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateTaskUnitTest {
    private Validator validator;
    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @InjectMocks
    TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    Task updatedTask = new Task(
        0,
        "Review blocked case",
        "Investigate the cause",
        Constants.Status.IN_PROGRESS,
        LocalDateTime.parse("2026-05-25T00:00:00"),
        LocalDateTime.parse("2026-05-22T00:00:00"),
        LocalDateTime.parse("2026-05-22T00:00:00")
    );

    TaskResponse updatedTaskDto = TaskResponse.builder()
        .id(0)
        .title("Review blocked case")
        .description("Investigate the cause")
        .status(Constants.Status.IN_PROGRESS)
        .dueTimestamp(LocalDateTime.parse("2026-05-25T00:00:00"))
        .createdTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
        .updatedTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
        .build();

    @Test
    void testUpdateSuccess() {
        when(taskRepository.updateTask(0, Constants.Status.IN_PROGRESS)).thenReturn(0);

        Integer response = taskService.updateTask(0, "IN_PROGRESS");

        assertThat(response).isEqualTo(0);
    }

    @Test
    void testInvalidStatus() {
        UpdateStatusRequest request = new UpdateStatusRequest("REJECTED");

        Set<ConstraintViolation<UpdateStatusRequest>> violations = validator.validate(request);
        assertThat(violations)
            .hasSize(1)
            .extracting(ConstraintViolation::getMessage)
            .contains("Status must be PENDING, IN_PROGRESS, or COMPLETED");

    }
}
