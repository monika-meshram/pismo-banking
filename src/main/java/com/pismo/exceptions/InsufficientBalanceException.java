package com.pismo.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String s) {
        super(s);
    }
}
