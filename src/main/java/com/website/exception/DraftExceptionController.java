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

    @ExceptionHandler(value = CannotJoinDraftException.class)
    public ResponseEntity<Object> cannotJoinDraftException(CannotJoinDraftException exception) {
        return new ResponseEntity<>("User was not invited or the draft has already started", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotCreatorOfDraftException.class)
    public ResponseEntity<Object> notCreatorOfDraftException(NotCreatorOfDraftException exception) {
        return new ResponseEntity<>("Sorry, You are not the owner of the draft", HttpStatus.UNAUTHORIZED);
    }
}
