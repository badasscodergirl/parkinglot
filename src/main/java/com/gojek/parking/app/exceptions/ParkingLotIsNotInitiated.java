package com.gojek.parking.app.exceptions;

public class ParkingLotIsNotInitiated extends Exception {

    public ParkingLotIsNotInitiated() {
        super("Cannot execute any action since parking lot is not initiated");
    }
}
