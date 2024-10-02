package com.github.balazs60.decline.advice;

import com.github.balazs60.decline.exception.EmptyInputException;
import com.github.balazs60.decline.exception.PasswordNotValidException;
import com.github.balazs60.decline.exception.UserNameAlreadyExistException;
import com.github.balazs60.decline.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException){

        return new ResponseEntity<>("Input field is empty, please look into it", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<String> handlePasswordIsNotNotValid(PasswordNotValidException passwordNotValidException){
        return new ResponseEntity<>("Password must be minimum 6 character long",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserIsNotFound(UserNotFoundException userNotFoundException){
        return  new ResponseEntity<>("User not found with this name", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ResponseEntity<String> handleUserNameAlreadyExist(UserNameAlreadyExistException userNameAlreadyExistException){
        return new ResponseEntity<>("Username already exist",HttpStatus.CONFLICT);
    }
}
