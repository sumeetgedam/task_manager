package com.sumeetgedam.task_manager.mappers.impl;

import com.sumeetgedam.task_manager.domain.dto.TaskListDto;
import com.sumeetgedam.task_manager.domain.entities.Task;
import com.sumeetgedam.task_manager.domain.entities.TaskList;
import com.sumeetgedam.task_manager.domain.entities.TaskStatus;
import com.sumeetgedam.task_manager.mappers.TaskListMapper;
import com.sumeetgedam.task_manager.mappers.TaskMapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }
    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream().map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream().map(taskMapper::toDto).toList())
                        .orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks){
        if(null == tasks || tasks.isEmpty()){
            return null;
        }

        long closedTaskedCount = tasks.stream().filter(task ->
                        TaskStatus.CLOSED == task.getStatus()
                ).count();
        return (double)(closedTaskedCount / tasks.size());
    }
}
