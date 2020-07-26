package com.gojek.parking.app.actions;

public class ParkingLotCreation extends Action {

    private final int capacity;

    public ParkingLotCreation(int capacity) {
        super(ActionType.CREATE_PARKING_LOT);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
