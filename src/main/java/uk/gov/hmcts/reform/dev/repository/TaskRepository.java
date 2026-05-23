package uk.gov.hmcts.reform.dev.repository;

import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.utils.Constants;

public interface TaskRepository extends CrudRepository<Task, Integer, Constants.Status> {}
