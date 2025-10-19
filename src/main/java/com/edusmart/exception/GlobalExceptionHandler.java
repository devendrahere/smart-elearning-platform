package com.edusmart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;

public class GlobalExceptionHandler {


    //to handle with username not found exceptions
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiException> handleUserNotFound(UsernameNotFoundException ex){
        ApiException error=new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    //to handle invalid credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiException> handleBadCredentials(BadCredentialsException e){
        ApiException error = new ApiException("Invalid user credentials",HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    //to handle error with jwt signature
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiException> handleJwtSignature(SignatureException e){
        ApiException error=new ApiException("Invalid jwt Signature",HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    //
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGeneric(Exception e){
        ApiException error=new ApiException("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
