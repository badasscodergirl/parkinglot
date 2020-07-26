package com.gojek.parking.app.exceptions;

public class InvalidActionNameException extends InvalidInputException {
    public InvalidActionNameException(String name) {
        super("Invalid action name "+name);
    }
}
