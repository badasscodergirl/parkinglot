package com.gojek.parking.app.exceptions;


import com.gojek.parking.app.model.Vehicle;

public class NoParkingSlotAvailable extends Exception {
    public NoParkingSlotAvailable(Vehicle vehicle) {
        super("No parking slot available for vehicle "+vehicle);
    }
}