package com.gojek.parking.app.exceptions;

public class InvalidInputException extends Exception {

    public InvalidInputException(String s) {
        super("Invalid input "+s);
    }
}
