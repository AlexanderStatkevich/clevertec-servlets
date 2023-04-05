package com.statkevich.receipttask.exceptions;

public class ProductNotExistException extends RuntimeException {

    public ProductNotExistException(String message) {
        super(message);
    }
}
