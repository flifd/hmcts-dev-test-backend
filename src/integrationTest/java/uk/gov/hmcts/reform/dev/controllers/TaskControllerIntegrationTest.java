package uk.gov.hmcts.reform.dev.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@DisplayName("Task Controller Integration Tests")
class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";
    }

    // ==================== Welcome Endpoint Tests ====================

    @Test
    void testWelcomeEndpoint() {

    }

    // ===================== Create task Tests =========================

    @Test
    void testCreateTaskSuccess() {

    }

    @Test
    void testCreateTaskWithoutDescription() {

    }

    @Test
    void testCreateTaskWithBlankTitle() {

    }

    @Test
    void testCreateTaskWithoutTitle() {

    }

    @Test
    void testCreateTaskWithoutStatus() {

    }

    @Test
    void testCreateTaskWithoutDueDateTime() {

    }

    @Test
    void testCreateTaskWithPastDueDateTime() {

    }

    // ========================= Get tasks ================================

    @Test
    void testGetAllTasks() {

    }

    @Test
    void testGetAllTasksEmpty() {

    }

    @Test
    void testGetTaskByIdSuccess() {

    }

    @Test
    void testGetTaskByIdNotFound() {

    }

    @Test
    void testGetTaskByIdInvalidFormat() {

    }

    // ======================== Update tasks =============================

    @Test
    void testUpdateTaskStatusSuccess() {

    }

    @Test
    void testUpdateTaskStatusToCompleted() {

    }

    @Test
    void testUpdateTaskStatusNotFound() {

    }

    @Test
    void testUpdateTaskStatusInvalid() {

    }

    // ==================== Delete tasks ====================

    @Test
    void testDeleteTaskSuccess() {

    }

    @Test
    void testDeleteTaskNotFound() {

    }
}



