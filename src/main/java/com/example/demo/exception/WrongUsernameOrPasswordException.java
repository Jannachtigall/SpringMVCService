package com.example.demo.exception;

import com.example.demo.consts.TextConsts;

public class WrongUsernameOrPasswordException extends RuntimeException{

    public WrongUsernameOrPasswordException() {
        super(TextConsts.wrongUsernameOrPassword);
    }

}
