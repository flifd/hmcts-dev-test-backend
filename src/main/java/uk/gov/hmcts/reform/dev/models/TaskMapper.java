package uk.gov.hmcts.reform.dev.models;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class );

    TaskResponse toDto(Task task);

    Task fromDto(TaskRequest taskDto);
}
