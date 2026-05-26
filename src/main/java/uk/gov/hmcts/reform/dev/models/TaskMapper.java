package uk.gov.hmcts.reform.dev.models;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uk.gov.hmcts.reform.dev.dtos.TaskRequest;
import uk.gov.hmcts.reform.dev.dtos.TaskResponse;
import uk.gov.hmcts.reform.dev.utils.Constants;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class );

    TaskResponse toDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status")
    Task fromDto(TaskRequest taskDto);

    default Constants.Status mapStatus(String status) {
        return Constants.Status.valueOf(status.toUpperCase());
    }
}
