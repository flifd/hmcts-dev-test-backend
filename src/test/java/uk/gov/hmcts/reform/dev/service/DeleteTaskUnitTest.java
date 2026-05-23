package uk.gov.hmcts.reform.dev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.utils.Utils;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTaskUnitTest {
    @Mock
    TaskRepository taskRepository;

    @Mock
    Utils utils;

    @InjectMocks
    TaskServiceImpl taskService;

    int validTaskId = 1;

    @Test
    void testDeleteTaskSuccess() {
        when(taskRepository.findById(validTaskId)).thenReturn(java.util.Optional.of(new Task()));
        when(utils.validateTaskId(validTaskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(validTaskId);

        taskService.deleteTask(validTaskId);

        verify(taskRepository).deleteById(validTaskId);
    }

    @Test
    void testDeleteTaskDoesNotExist() {
        when(utils.validateTaskId(9999)).thenReturn(true);
        when(taskRepository.findById(9999)).thenReturn(java.util.Optional.empty());

        assertThrows(
            TaskNotFoundException.class, () -> {
                taskService.deleteTask(9999);
            });
    }

    @Test
    void testDeleteInvalidTask() {
        when(utils.validateTaskId(-1)).thenReturn(false);

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
