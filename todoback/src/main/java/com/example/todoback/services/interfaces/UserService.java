package com.example.todoback.services.interfaces;

import com.example.todoback.models.user.User;
import com.example.todoback.models.user.UserRequest;

public interface UserService {

    User getUserByEmail(String email);
    void registerUser(UserRequest userRequest);

}
