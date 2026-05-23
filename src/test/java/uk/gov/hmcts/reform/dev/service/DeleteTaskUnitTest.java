package uk.gov.hmcts.reform.dev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTaskUnitTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    int validTaskId = 1;

    @Test
    void testDeleteTaskSuccess() {
        doNothing().when(taskRepository).deleteById(validTaskId);

        taskService.deleteTask(validTaskId);

        verify(taskRepository).deleteById(validTaskId);
    }

    @Test
    void testDeleteTaskDoesNotExist() {
        doThrow(new TaskNotFoundException("Task not found with id: 9999")).when(taskRepository).deleteById(9999);

        assertThrows(
            TaskNotFoundException.class, () -> {
                taskService.deleteTask(9999);
            });
    }

    @Test
    void testDeleteInvalidTask() {
        assertThrows(
            InvalidParameterException.class, () -> {
                taskService.deleteTask(-1);
            });
    }

    @Test
    void testDeleteInvalidTypeTask() {
        assertThrows(
            NumberFormatException.class, () -> {
                taskService.deleteTask(Integer.parseInt("string"));
            });
    }
}
