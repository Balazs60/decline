package com.github.balazs60.decline.exception;

import lombok.Data;

@Data

public class PasswordNotValidException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public PasswordNotValidException(String errorCode, String errorMessage){
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
