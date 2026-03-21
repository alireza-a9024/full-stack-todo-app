package com.example.todoback.controllers;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoback.models.task.Status;
import com.example.todoback.models.task.TaskRequest;
import com.example.todoback.models.task.TaskResponse;
import com.example.todoback.services.interfaces.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {
    @Autowired
    private TaskService taskService;



    @GetMapping
    public ResponseEntity<List<TaskResponse>> getMyTasks(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(taskService.getMyTasks(email));

    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest, Principal principal){
        String email = principal.getName();
        return ResponseEntity.ok(taskService.createTask(taskRequest, email));
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<TaskResponse> updateTaskTitle(@PathVariable UUID id, @RequestBody TaskRequest taskRequest , Principal principal){
        String email = principal.getName();
        String newTitle = taskRequest.getTitle();
        return ResponseEntity.ok(taskService.updateTaskTitle(id, newTitle , email));

    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable UUID id, @RequestBody TaskRequest taskRequest, Principal principal){
        String email = principal.getName();
        Status newStatus = taskRequest.getStatus();
        return ResponseEntity.ok(taskService.updateTaskStatus(id, newStatus, email));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id, Principal principal){

        String email = principal.getName();
        taskService.deleteTask(id, email);
        return ResponseEntity.ok("Task deleted successfully");
        

    }
    
}
