package com.sumeetgedam.task_manager.domain.dto;

public record ErrorResponse(int status,
        String message,
        String details
) {

}
