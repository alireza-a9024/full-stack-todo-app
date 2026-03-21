package com.example.todoback.models.task;

import java.util.UUID;

import lombok.Data;

@Data
public class TaskResponse {
    private UUID id;
    private String title;
    private Status status;
}
