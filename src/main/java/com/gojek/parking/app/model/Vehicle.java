package com.gojek.parking.app.model;

public abstract class Vehicle {
    private String color;
    private String registrationNo;

    private int slotNum = -1;

    Vehicle(String color, String registrationNo) {
        this.color = color.toUpperCase();
        this.registrationNo = registrationNo.toUpperCase();
    }


    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public String getColor() {
        return this.color;
    }

    public void parked(int slotNum) {
        this.slotNum = slotNum;
    }

    public int parkedSlot() {
        return this.slotNum;
    }

    @Override
    public String toString() {
        return "Color = "+color+", Reg No = "+ registrationNo;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vehicle) || this.getClass() != obj.getClass()) return false;
        Vehicle other = (Vehicle) obj;
        return (other.registrationNo.equals(this.registrationNo) && other.color.equals(this.color));
    }
}
