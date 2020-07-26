package com.gojek.parking.app.actions;

public class Leave extends Action {

    private final int slotNumber;
    public Leave(int slotNumber) {
        super(ActionType.LEAVE);
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() {
        return this.slotNumber;
    }
}
