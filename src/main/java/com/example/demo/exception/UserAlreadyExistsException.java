package com.example.demo.exception;

import com.example.demo.consts.TextConsts;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super(TextConsts.alreadyRegistered);
    }

}
