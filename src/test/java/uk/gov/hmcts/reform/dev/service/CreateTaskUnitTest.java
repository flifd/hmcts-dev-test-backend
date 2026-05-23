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
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.utils.Constants;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTaskUnitTest {
    private Validator validator;
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ===================== Valid task tests =========================

    @Test
    void testCreateTaskSuccess() {
        Task task = new Task(
            0,
            "Review blocked case",
            "Investigate the cause",
            Constants.Status.PENDING,
            LocalDateTime.parse("2026-05-25T00:00:00"),
            LocalDateTime.parse("2026-05-22T00:00:00"),
            LocalDateTime.parse("2026-05-22T00:00:00")
        );
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueTimeStamp(LocalDateTime.parse("2026-05-25T00:00:00"))
            .build();

        TaskResponse expectedTaskRes = TaskResponse.builder()
            .id(0)
            .title("Review blocked case")
            .description("Investigate the cause")
            .status(Constants.Status.PENDING)
            .dueTimestamp(LocalDateTime.parse("2026-05-25T00:00:00"))
            .createdTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
            .updatedTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
            .build();

        TaskResponse taskRes = taskService.createTask(taskReq);
        assertThat(taskRes).isEqualTo(expectedTaskRes);
    }

    @Test
    void testCreateTaskWithoutDescription() {
        Task task = new Task(
            0,
            "Review blocked case",
            null,
            Constants.Status.PENDING,
            LocalDateTime.parse("2026-05-25T00:00:00"),
            LocalDateTime.parse("2026-05-22T00:00:00"),
            LocalDateTime.parse("2026-05-22T00:00:00")
        );
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse expectedTaskRes = TaskResponse.builder()
            .id(0)
            .title("Review blocked case")
            .description(null)
            .status(Constants.Status.PENDING)
            .dueTimestamp(LocalDateTime.parse("2026-05-25T00:00:00"))
            .createdTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
            .updatedTimestamp(LocalDateTime.parse("2026-05-22T00:00:00"))
            .build();

        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .status("PENDING")
            .dueTimeStamp(LocalDateTime.now().plusDays(1))
            .build();

        TaskResponse taskRes = taskService.createTask(taskReq);
        assertThat(taskRes).isEqualTo(expectedTaskRes);
    }

    // ===================== Invalid task tests =========================

    @Test
    void testBlankTitle() {
        TaskRequest request = TaskRequest.builder()
            .title("")
            .description("Investigate the cause")
            .status("PENDING")
            .dueTimeStamp(LocalDateTime.now().plusDays(1))
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertThat(violations)
            .hasSize(1)
            .extracting(ConstraintViolation::getMessage)
            .contains("Title is required and cannot be blank");
    }

    @Test
    void testNullTitle() {
        TaskRequest request = TaskRequest.builder()
            .title(null)
            .description("Investigate the cause")
            .status("PENDING")
            .dueTimeStamp(LocalDateTime.now().plusDays(1))
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testNullStatus() {
        TaskRequest request = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status(null)
            .dueTimeStamp(LocalDateTime.now().plusDays(1))
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertThat(violations)
            .hasSize(1)
            .extracting(ConstraintViolation::getMessage)
            .contains("Status is required");
    }

    @Test
    void testNullDateTime() {
        TaskRequest request = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueTimeStamp(null)
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertThat(violations)
            .hasSize(1)
            .extracting(ConstraintViolation::getMessage)
            .contains("Due date and time is required");
    }

    @Test
    void testPastDueDateTime() {
        TaskRequest request = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueTimeStamp(LocalDateTime.now().minusDays(1))
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertThat(violations)
            .hasSize(1)
            .extracting(ConstraintViolation::getMessage)
            .contains("Due date and time must be in the present or future");
    }

    @Test
    void testMultipleViolations() {
        TaskRequest request = TaskRequest.builder()
            .title("")
            .description("Some description")
            .status(null)
            .dueTimeStamp(LocalDateTime.now().minusHours(1))
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(3);
    }
}
