package com.logixtech.vote.controller.interfaces;

import com.logixtech.vote.dto.AuthTokensDto;
import com.logixtech.vote.dto.CredentialsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public interface IAuthenticationController {

    @PostMapping("/login")
    ResponseEntity<AuthTokensDto> login(@RequestBody CredentialsDto credentials);

}
