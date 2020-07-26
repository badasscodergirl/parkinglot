package com.gojek.parking.app.exceptions;

public class InvalidActionException extends Exception {

    public InvalidActionException(String error) {
        super(error);
    }
}
