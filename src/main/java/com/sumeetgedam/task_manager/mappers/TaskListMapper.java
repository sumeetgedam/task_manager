package com.sumeetgedam.task_manager.mappers;

import com.sumeetgedam.task_manager.domain.dto.TaskListDto;
import com.sumeetgedam.task_manager.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
