package com.example.todoback.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.todoback.models.task.Status;
import com.example.todoback.models.task.TaskRequest;
import com.example.todoback.models.task.TaskResponse;

public interface TaskService {
    
    TaskResponse createTask(TaskRequest taskRequest, String email);

    List<TaskResponse> getMyTasks(String email);

    TaskResponse updateTaskTitle(UUID id, String newTitle, String email);

    TaskResponse updateTaskStatus(UUID id, Status newStatus, String email);

    Void deleteTask(UUID id, String email);
}
