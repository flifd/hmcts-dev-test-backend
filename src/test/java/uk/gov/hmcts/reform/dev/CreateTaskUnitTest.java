package uk.gov.hmcts.reform.dev;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.service.TaskServiceImpl;
import uk.gov.hmcts.reform.dev.utils.Constants;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTaskUnitTest {
    private Validator validator;
    TaskServiceImpl taskService = new TaskServiceImpl();

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ===================== Valid task tests =========================

    @Test
    void testCreateTaskSuccess() {
        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .description("Investigate the cause")
            .status("PENDING")
            .dueDateTime(LocalDateTime.now().plusDays(1))
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(taskReq);
        assertThat(violations).isEmpty();
    }

    @Test
    void testCreateTaskWithoutDescription() {
        TaskRequest taskReq = TaskRequest.builder()
            .title("Review blocked case")
            .status("PENDING")
            .dueDateTime(LocalDateTime.now().plusDays(1))
            .build();
        TaskResponse taskRes = new TaskResponse(
            0,
            "Task Title",
            null,
            Constants.Status.PENDING,
            LocalDateTime.parse("2026-05-24T10:15:30"),
            LocalDateTime.parse("2026-05-24T10:15:30"),
            LocalDateTime.parse("2026-05-24T10:15:30")
        );

        TaskResponse response = taskService.createTask(taskReq);
        assertThat(response).isEqualTo(taskRes);
    }

    // ===================== Invalid task tests =========================

    @Test
    void testBlankTitle() {
        TaskRequest request = TaskRequest.builder()
            .title("")
            .description("Investigate the cause")
            .status("PENDING")
            .dueDateTime(LocalDateTime.now().plusDays(1))
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
            .dueDateTime(LocalDateTime.now().plusDays(1))
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
            .dueDateTime(LocalDateTime.now().plusDays(1))
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
            .dueDateTime(null)
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
            .dueDateTime(LocalDateTime.now().minusDays(1))
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
            .dueDateTime(LocalDateTime.now().minusHours(1))
            .build();

        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(3);
    }
}
