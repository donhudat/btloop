package com.example.demo2.controller;

import com.example.demo2.request.AuthRequest;
import com.example.demo2.dto.UserDTO;
import com.example.demo2.model.User;
import com.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> registerAccount(@Valid @RequestBody UserDTO userDto) {
        if (isPasswordLengthInvalid(userDto.getPassword())) {
            return null;
        }
        System.out.println(userDto);
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest userDto) {
        return ResponseEntity.ok(userService.login(userDto));
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return password.length() < 4;
    }
}
