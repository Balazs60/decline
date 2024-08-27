package com.github.balazs60.decline.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public UserNotFoundException(String errorCode, String errorMessage){
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
