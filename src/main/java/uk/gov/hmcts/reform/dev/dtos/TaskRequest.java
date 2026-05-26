package uk.gov.hmcts.reform.dev.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for tasks")
public class TaskRequest {
    @NotBlank(message = "Title is required and cannot be blank")
    @Schema(description = "The title of the task", example = "Review blocked case")
    private String title;

    @Schema(description = "Optional description of the task", example = "Investigate the cause of the case's blockage")
    private String description;

    @NotNull(message = "Status is required")
    @Schema(description = "The status of the task", example = "PENDING", allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED"})
    private String status;

    @NotNull(message = "Due date and time is required")
    @FutureOrPresent(message = "Due date and time must be in the present or future")
    @Schema(description = "The due date and time for the task", example = "2026-06-22 14:30:00")
    private LocalDateTime dueTimestamp;

    @Schema(description = "The date and time the task was created", example = "2026-06-22 14:30:00")
    private LocalDateTime createdTimestamp;

    @Schema(description = "The date and time the task was last updated", example = "2026-06-22 14:30:00")
    private LocalDateTime updatedTimestamp;
}
