package com.example.demo.exception;

import com.example.demo.consts.TextConsts;

public class ChangePasswordException extends RuntimeException{

    public ChangePasswordException(){
        super(TextConsts.wrongUsernameOrPassword);
    }

}
