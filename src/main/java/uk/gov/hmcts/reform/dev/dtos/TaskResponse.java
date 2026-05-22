package uk.gov.hmcts.reform.dev.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.reform.dev.utils.Constants.Status;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO for tasks")
public class TaskResponse {
    @Schema(description = "The unique identifier of the task", example = "1")
    private int id;

    @Schema(description = "The title of the task", example = "Review blocked case")
    private String title;

    @Schema(description = "Optional description of the task", example = "Investigate the cause of the case's blockage")
    private String description;

    @Schema(description = "The status of the task", example = "PENDING")
    private Status status;

    @Schema(description = "The due date and time for the task", example = "2026-06-22T14:30:00")
    private LocalDateTime dueDateTime;

    @Schema(description = "The timestamp when the task was created", example = "2026-05-22T10:00:00")
    private LocalDateTime createdDateTime;

    @Schema(description = "The timestamp when the task was last updated", example = "2026-05-22T10:00:00")
    private LocalDateTime updatedDateTime;
}
