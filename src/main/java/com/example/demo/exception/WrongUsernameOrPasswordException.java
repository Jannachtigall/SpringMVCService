package com.example.demo.exception;

import com.example.demo.contsts.TextConsts;

public class WrongUsernameOrPasswordException extends RuntimeException{

    public WrongUsernameOrPasswordException() {
        super(TextConsts.wrongUsernameOrPassword);
    }

}
