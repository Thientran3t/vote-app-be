package com.logixtech.vote.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailTakenException extends RuntimeException {

    public EmailTakenException() {
        super("Username already exists");
    }

}
