package com.uqai.demo.app.uqaidemo.controller;

import com.uqai.demo.app.uqaidemo.config.security.AuthCredential;
import com.uqai.demo.app.uqaidemo.dto.ResponseError;
import com.uqai.demo.app.uqaidemo.dto.user.UserDto;
import com.uqai.demo.app.uqaidemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthCredential authCredential) throws ResponseError {
        return userService.login(authCredential);
    }
    @PostMapping("/register")
    UserDto createUser(@RequestBody UserDto userDto) throws ResponseError {
        return userService.createUser(userDto);
    }
}
