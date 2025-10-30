package com.edusmart.exception;

public class ResourcesNotFound extends RuntimeException{
    public ResourcesNotFound(String message){
        super(message);
    }
}
