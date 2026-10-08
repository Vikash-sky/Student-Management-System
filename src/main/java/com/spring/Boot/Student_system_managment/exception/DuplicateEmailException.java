package com.spring.Boot.Student_system_managment.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String msg){
        super(msg);
    }
}
