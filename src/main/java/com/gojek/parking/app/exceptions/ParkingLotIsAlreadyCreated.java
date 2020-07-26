package com.gojek.parking.app.exceptions;

public class ParkingLotIsAlreadyCreated extends Exception {

    public ParkingLotIsAlreadyCreated() {
        super("Parking lot is already created");
    }
}
