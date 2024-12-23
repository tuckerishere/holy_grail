package com.secretcow.holygrail.controllers;

import com.secretcow.holygrail.dtos.user.RegisterUserDto;
import com.secretcow.holygrail.exceptions.user.InvalidUserException;
import com.secretcow.holygrail.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(RegisterUserDto registerUserDto) throws InvalidUserException {
        userService.createNewUser(registerUserDto);
        return new ResponseEntity<>("New User Created", HttpStatus.CREATED);
    }
}
