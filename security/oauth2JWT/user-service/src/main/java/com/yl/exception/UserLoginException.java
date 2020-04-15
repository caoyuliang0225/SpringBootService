package com.yl.exception;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */
public class UserLoginException extends RuntimeException{

    private static final long serialVersionUID = -5649829716597415897L;

    public UserLoginException(String message) {
        super(message);
    }

}
