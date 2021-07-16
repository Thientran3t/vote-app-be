package com.logixtech.vote.controller;

import com.logixtech.vote.controller.interfaces.IUserController;
import com.logixtech.vote.dto.UserDto;
import com.logixtech.vote.dto.request.UserRequest;
import com.logixtech.vote.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto registerUser(UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @Override
    public UserDto getUser(String email) {
        return userService.getUser(email);
    }
}
