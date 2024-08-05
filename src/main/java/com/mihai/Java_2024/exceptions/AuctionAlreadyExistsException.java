package com.mihai.Java_2024.exceptions;

public class AuctionAlreadyExistsException extends Exception{
    private static final long serialVersionUID = 1L;
    public AuctionAlreadyExistsException(String message) {
        super(message);
    }
}
