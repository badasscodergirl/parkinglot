package com.gojek.parking.app.exceptions;

public class EmptyInputException extends InvalidInputException {
    public EmptyInputException() {
        super("EMPTY_INPUT");
    }
}
