package com.sprintboot.product.exception;

public class RecordNotExistException extends RuntimeException {
    public RecordNotExistException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public RecordNotExistException(String exMessage) {
        super(exMessage);
    }
}