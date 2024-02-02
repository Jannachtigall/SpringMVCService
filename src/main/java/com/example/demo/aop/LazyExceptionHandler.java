package com.example.demo.aop;

import com.example.demo.consts.TextConsts;
import com.example.demo.exception.ChangePasswordException;
import com.example.demo.exception.PasswordsDontMatchException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.WrongUsernameOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LazyExceptionHandler {

    @ExceptionHandler(PasswordsDontMatchException.class)
    public ResponseEntity<String> passwordsHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TextConsts.thePasswordsDontMatch);
    }

    @ExceptionHandler(WrongUsernameOrPasswordException.class)
    public ResponseEntity<String> usernameHandler() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(TextConsts.wrongUsernameOrPassword);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> existsHandler() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(TextConsts.alreadyRegistered);
    }

    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<String> changeHandler() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(TextConsts.wrongUsernameOrPassword);
    }
}
