package com.github.balazs60.decline.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class EmptyInputException extends RuntimeException{

    private String errorCode;
    private String errorMessage;



    public EmptyInputException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }





}
