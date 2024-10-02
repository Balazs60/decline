package com.github.balazs60.decline.exception;

import lombok.Data;

@Data

public class UserNameAlreadyExistException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public UserNameAlreadyExistException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
