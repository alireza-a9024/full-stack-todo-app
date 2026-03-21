package com.example.todoback.models.user;

import lombok.Data;

@Data
public class UserRequest {
    
    private String email;
    private String password;
    private String firstname;
    private String lastname;
}
