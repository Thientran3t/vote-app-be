package com.logixtech.vote.controller;

import com.logixtech.vote.controller.interfaces.IAuthenticationController;
import com.logixtech.vote.dto.AuthTokensDto;
import com.logixtech.vote.dto.CredentialsDto;
import com.logixtech.vote.dto.UserDto;
import com.logixtech.vote.security.jwt.JwtTokenUtil;
import com.logixtech.vote.service.CustomUserDetailService;
import com.logixtech.vote.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements IAuthenticationController {

    private JwtTokenUtil jwtTokenUtil;

    private CustomUserDetailService customUserDetailService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserService userService;

    public AuthenticationController(JwtTokenUtil jwtTokenUtil, CustomUserDetailService customUserDetailService, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailService = customUserDetailService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<AuthTokensDto> login(CredentialsDto credentials) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateToken(customUserDetailService.loadUserByUsername(credentials.getEmail()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        UserDto user = userService.getUser(credentials.getEmail());
        return new ResponseEntity<>(new AuthTokensDto(jwt, user), httpHeaders, HttpStatus.OK);
    }
}
