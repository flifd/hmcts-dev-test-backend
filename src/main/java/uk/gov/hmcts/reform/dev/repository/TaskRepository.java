package uk.gov.hmcts.reform.dev.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.utils.Constants;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer, Constants.Status> {
    @Override
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    Optional<Task> updateTask(Integer id, Constants.Status status);
}
