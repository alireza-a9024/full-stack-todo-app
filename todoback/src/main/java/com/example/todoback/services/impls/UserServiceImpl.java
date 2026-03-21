package com.example.todoback.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todoback.models.user.User;
import com.example.todoback.models.user.UserRequest;
import com.example.todoback.repositories.UserRepository;
import com.example.todoback.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void registerUser(UserRequest userRequest){
        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setFirstname(userRequest.getFirstname());
        newUser.setLastname(userRequest.getLastname());
        userRepository.save(newUser);
        
    }
    







}
