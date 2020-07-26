package com.gojek.parking.app.constants;

public class Messages {

    public static final String NOT_FOUND = "Not found.";

    public static String parkingLotSuccessMessage(int capacity) {
        return "Created parking lot with "+capacity+" slots";
    }
}
