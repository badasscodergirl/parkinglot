package com.gojek.parking.app.service;

import com.gojek.parking.app.exceptions.InvalidActionException;
import com.gojek.parking.app.exceptions.NoParkingSlotAvailable;
import com.gojek.parking.app.exceptions.VehicleIsAlreadyParked;
import com.gojek.parking.app.model.Vehicle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class ParkingSlotsManager<V extends Vehicle> {

    private final int capacity;
    private SortedSet<Integer> availableSpaces;
    private Map<Integer, V> parkedVehicles;

    public ParkingSlotsManager(int capacity) {
        this.capacity = capacity;
        this.availableSpaces = new ConcurrentSkipListSet<>();
        this.parkedVehicles = new ConcurrentHashMap<>();

        for(int i = 1; i <= capacity; i++) {
            this.availableSpaces.add(i);
        }
    }

    public boolean park(V vehicle) throws NoParkingSlotAvailable, VehicleIsAlreadyParked {
        /*if(parkedVehicles.size() == capacity) {
            throw new NoParkingSlotAvailable(vehicle);
        }*/
        if(availableSpaces.isEmpty()) {
            throw new NoParkingSlotAvailable(vehicle);
        }

        Set<String> alreadyParkedNumbers = parkedVehicles.values().stream().map(Vehicle::getRegistrationNo).collect(Collectors.toSet());
        if(alreadyParkedNumbers.contains(vehicle.getRegistrationNo())) {
            throw new VehicleIsAlreadyParked(vehicle.getRegistrationNo());
        }

        int available = availableSpaces.first();
        vehicle.parked(available);
        availableSpaces.remove(available);
        parkedVehicles.put(available, vehicle);

        System.out.println("Allocated slot number: "+available);

        return true;
    }

    public void leave(int slotNumber) throws InvalidActionException {
        if(slotNumber < 1 || slotNumber > capacity) {
            throw new InvalidActionException("Slot number "+slotNumber+" is invalid");
        }

        if(!parkedVehicles.isEmpty() && parkedVehicles.containsKey(slotNumber)) {
            parkedVehicles.remove(slotNumber);
            availableSpaces.add(slotNumber);
        }

        System.out.println("Slot number "+slotNumber+" is free");

    }

    public void showStatus() {
        String space = "\t";
        System.out.println("Slot No."+space+" Registration No"+space+" Color");
        for(int slotNum: parkedVehicles.keySet()) {
            Vehicle v = parkedVehicles.get(slotNum);
            System.out.println(slotNum + space + v.getRegistrationNo() + space + v.getColor());
        }
    }

    public List<Integer> getSlotNumbersForColor(String color) {
        return getVehiclesForColor(color).stream().map(Vehicle::parkedSlot).collect(Collectors.toList());
    }

    public int getSlotNumberForRegNum(String regNum) {
        int slotNum = 0;
        for(int slk: parkedVehicles.keySet()) {
            V vehicle = parkedVehicles.get(slk);
            if(vehicle.getRegistrationNo().equalsIgnoreCase(regNum)) {
                slotNum = slk;
                break;
            }
        }
        return slotNum;
    }

    public List<String> getRegNumsForColor(String color) {
       return getVehiclesForColor(color).stream().map(Vehicle::getRegistrationNo).collect(Collectors.toList());
    }

    public int getRemainingCapacity() {
        if(this.capacity == 0) return 0;

        return this.availableSpaces.size();
    }

    private List<V> getVehiclesForColor(String color) {
        List<V> vehicles = new ArrayList<>();
        for(int slotNum: parkedVehicles.keySet()) {
            V vehicle = parkedVehicles.get(slotNum);
            if(vehicle.getColor().equalsIgnoreCase(color)) {
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

}
