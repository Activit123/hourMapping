package com.mihai.Java_2024.exceptions;

public class CifIsNotInCorrectFormatException extends RuntimeException{
    public CifIsNotInCorrectFormatException(String message){
        super(message);
    }
}
