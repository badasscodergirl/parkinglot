package com.gojek.parking.app.actions;

public class RegistrationsNumsForColors extends Action {

    private String color;
    public RegistrationsNumsForColors(String color) {
        super(ActionType.REGISTRATION_NUMS_FOR_COLOR);
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
