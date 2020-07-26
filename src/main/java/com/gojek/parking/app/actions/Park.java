package com.gojek.parking.app.actions;

import com.gojek.parking.app.model.Vehicle;

public class Park<V extends Vehicle> extends Action {

    private final V vehicle;

    public Park(V vehicle) {
        super(ActionType.PARK);
        this.vehicle = vehicle;
    }

    public V getVehicle() {
        return this.vehicle;
    }
}
