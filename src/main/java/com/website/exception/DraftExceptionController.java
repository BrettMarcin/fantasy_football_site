package com.website.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DraftExceptionController {
    @ExceptionHandler(value = TooManyDraftException.class)
    public ResponseEntity<Object> exception(TooManyDraftException exception) {
        return new ResponseEntity<>("Player has created to many Drafts", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ExpiredToken.class)
    public ResponseEntity<Object> expiredToken(ExpiredToken exception) {
        return new ResponseEntity<>("Token has expired", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameInvalidException.class)
    public ResponseEntity<Object> usernameInvalidException(UsernameInvalidException exception) {
        return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmailInvalidException.class)
    public ResponseEntity<Object> emailInvalidException(EmailInvalidException exception) {
        return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
    }
}
