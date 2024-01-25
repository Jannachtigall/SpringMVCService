package com.example.demo.exception;

import com.example.demo.contsts.TextConsts;

public class PasswordsDontMatchException extends RuntimeException{

    public PasswordsDontMatchException(){
        super(TextConsts.thePasswordsDontMatch);
    }

}
