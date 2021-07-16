package com.logixtech.vote.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LimitVotePostException extends RuntimeException {
    public LimitVotePostException(String message) {
        super(message);
    }
}
