package uk.gov.hmcts.reform.dev.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard error response format")
public class ErrorResponse {
    @Schema(description = "The HTTP status code", example = "400")
    private int status;

    @Schema(description = "Error message", example = "Title must be provided")
    private String message;

    @Schema(description = "Detailed error description", example = "Validation failed for field 'title'")
    private String detail;

    @Schema(description = "The timestamp when the error occurred", example = "2026-05-22T10:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "The request path that caused the error", example = "/tasks")
    private String path;
}

