package com.logixtech.vote.controller.interfaces;

import com.logixtech.vote.dto.UserDto;
import com.logixtech.vote.dto.request.UserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public interface IUserController {

    @PostMapping("/register")
    UserDto registerUser(@RequestBody UserRequest userRequest);

    @GetMapping("/get-user")
    UserDto getUser(@RequestParam String email);

}
