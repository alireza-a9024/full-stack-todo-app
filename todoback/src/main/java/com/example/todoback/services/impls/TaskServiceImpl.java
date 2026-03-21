package com.example.todoback.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todoback.models.task.Status;
import com.example.todoback.models.task.Task;
import com.example.todoback.models.task.TaskRequest;
import com.example.todoback.models.task.TaskResponse;
import com.example.todoback.models.user.User;
import com.example.todoback.repositories.TaskRepository;
import com.example.todoback.repositories.UserRepository;
import com.example.todoback.services.interfaces.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskResponse createTask(TaskRequest taskRequest, String email) {
       User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        
        task.setUser(user);
        task.setTitle(taskRequest.getTitle());
        task.setStatus(taskRequest.getStatus());
        taskRepository.save(task);

        //package the output payload. called DTO daata transfer object
        TaskResponse output = new TaskResponse();
        output.setId(task.getId());
        output.setTitle(task.getTitle());
        output.setStatus(task.getStatus());

        return output;
        
    }

    


    @Override
    public List<TaskResponse> getMyTasks(String email) {
        
        List<TaskResponse> output = new ArrayList<>();
        
        List<Task> tasks = taskRepository.findByUserEmail(email);
        for (Task task : tasks){
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setId(task.getId());
            taskResponse.setTitle(task.getTitle());
            taskResponse.setStatus(task.getStatus());
            output.add(taskResponse);

    }
        
        return output;
        
    }



    //email comes from the token
    private Task getTasksIfOwned(UUID id, String email){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("UNAUTHORIZED: Task with id: " + id + " is not owned by user with email: " + email);
        }
        return task;
    }

    @Override
    public TaskResponse updateTaskTitle(UUID id, String newTitle, String email){
        Task taskDb = getTasksIfOwned(id, email);
        taskDb.setTitle(newTitle);
        taskRepository.save(taskDb);

        TaskResponse output = new TaskResponse();
        output.setId(taskDb.getId());
        output.setTitle(taskDb.getTitle());
        output.setStatus(taskDb.getStatus());
        return output;

    }



    
    @Override
    public TaskResponse updateTaskStatus(UUID id, Status newStatus, String email) {
        Task taskDb = getTasksIfOwned(id,email);
        taskDb.setStatus(newStatus);
        taskRepository.save(taskDb);
        
        TaskResponse output = new TaskResponse();
        output.setId(taskDb.getId());
        output.setTitle(taskDb.getTitle());
        output.setStatus(taskDb.getStatus());
        return output;

    }

    @Override
    public Void deleteTask(UUID id, String email){
        Task taskDb = getTasksIfOwned(id, email);
        taskRepository.delete(taskDb);
        return null;
    }

}
