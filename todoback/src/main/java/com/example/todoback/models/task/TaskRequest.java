package com.example.todoback.models.task;

import lombok.Data;

@Data
public class TaskRequest {

    private String title;
    private Status status;

}
