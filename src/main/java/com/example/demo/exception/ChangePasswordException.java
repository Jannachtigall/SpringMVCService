package com.example.demo.exception;

import com.example.demo.contsts.TextConsts;

public class ChangePasswordException extends RuntimeException{

    public ChangePasswordException(){
        super(TextConsts.wrongUsernameOrPassword);
    }

}
