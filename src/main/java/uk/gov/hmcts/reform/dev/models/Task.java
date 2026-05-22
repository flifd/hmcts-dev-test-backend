package uk.gov.hmcts.reform.dev.models;

import jakarta.persistence.*;
import lombok.*;
import uk.gov.hmcts.reform.dev.utils.Constants.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime dueTimestamp;
    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;
}
