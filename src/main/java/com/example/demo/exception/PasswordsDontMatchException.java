package com.example.demo.exception;

import com.example.demo.consts.TextConsts;

public class PasswordsDontMatchException extends RuntimeException{

    public PasswordsDontMatchException(){
        super(TextConsts.thePasswordsDontMatch);
    }

}
