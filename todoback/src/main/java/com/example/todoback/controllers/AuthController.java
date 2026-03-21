package com.example.todoback.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoback.models.user.UserLogin;
import com.example.todoback.models.user.UserRequest;
import com.example.todoback.security.JwtTokenUtil;
import com.example.todoback.security.JwtUserDetailsService;
import com.example.todoback.services.interfaces.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {


    @Autowired
    private UserService userService;

    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest){
        try {
            userService.registerUser(userRequest);
        return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("This email already exists"+e.getMessage());
        }
    }


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));

        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Incorrect email or password");
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userLogin.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        var myUser = userService.getUserByEmail(userLogin.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("firstname", myUser.getFirstname());
        response.put("lastname", myUser.getLastname());


        return ResponseEntity.ok(response);

    }


    
    
}
