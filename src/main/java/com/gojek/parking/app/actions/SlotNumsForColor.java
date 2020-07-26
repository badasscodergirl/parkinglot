package com.gojek.parking.app.actions;

public class SlotNumsForColor extends Action {

    private String color;

    public SlotNumsForColor(String color) {
        super(ActionType.SLOT_NUMBERS_FOR_COLOR);
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
