package com.statkevich.receipttask.exceptions;

public class DiscountCardNotExistException extends RuntimeException {
    public DiscountCardNotExistException(String message) {
        super(message);
    }
}
