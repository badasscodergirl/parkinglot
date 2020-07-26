package com.gojek.parking.app.exceptions;

public class VehicleIsAlreadyParked extends Exception {
    public VehicleIsAlreadyParked(String vehicleNum) {
        super("Vehicle with registration number "+vehicleNum+" is already parked");
    }
}
