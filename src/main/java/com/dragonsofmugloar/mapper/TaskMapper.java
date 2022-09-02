package com.dragonsofmugloar.mapper;

import com.dragonsofmugloar.domain.Task;
import com.dragonsofmugloar.dto.TaskDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {

    Task toEntity(TaskDTO taskDTO);

    List<Task> toEntityCollection(List<TaskDTO> taskDTOList);
}
