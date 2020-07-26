package com.gojek.parking.app.service;


import com.gojek.parking.app.actions.*;
import com.gojek.parking.app.constants.Messages;
import com.gojek.parking.app.exceptions.*;
import com.gojek.parking.app.model.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

public class ParkingService<V extends Vehicle> {

    private ParkingSlotsManager<V> parkingSlotsManager;

    public ParkingService() {}

    public void executeActions(List<Action> actions) {
        for(Action action: actions) {
            try {
                executeAction(action);
            } catch (InvalidActionException | NoParkingSlotAvailable | FailedToExecuteAction ex) {
                System.out.println("Exception occurred while executing action "+ex.getMessage());
            }
        }
    }

    public void executeAction(Action action)
            throws InvalidActionException, NoParkingSlotAvailable, FailedToExecuteAction {
        try {
            switch (action.actionType()) {

                case CREATE_PARKING_LOT:
                    ParkingLotCreation parkingLotCreation = (ParkingLotCreation) action;
                    createParkingLot(parkingLotCreation.getCapacity());
                    break;

                case PARK:
                    Park<V> parkAction = (Park<V>) action;
                    parkVehicle(parkAction.getVehicle());
                    break;

                case LEAVE:
                    Leave leaveAction = (Leave) action;
                    leaveVehicle(leaveAction.getSlotNumber());
                    break;

                case SHOW_CURRENT_STATUS:
                    showCurrentStatus();
                    break;

                case SLOT_NUMBERS_FOR_COLOR:
                    SlotNumsForColor slotNumsForColorAction = (SlotNumsForColor) action;
                    slotNumbersForColor(slotNumsForColorAction.getColor());
                    break;

                case SLOT_NUMBER_FOR_REGISTRATION_NUM:
                    SlotNumForRegistrationNum slotNumForRegistrationNumAction = (SlotNumForRegistrationNum) action;
                    slotNumbersForRegNum(slotNumForRegistrationNumAction.getRegistrationNum());
                    break;

                case REGISTRATION_NUMS_FOR_COLOR:
                    RegistrationsNumsForColors registrationsNumsForColorsAction = (RegistrationsNumsForColors) action;
                    regNumsForColor(registrationsNumsForColorsAction.getColor());
                    break;

                case EXIT:
                    exit();
                    break;

                default:
                    throw new InvalidActionException(action.actionType().getActionName());
            }
        } catch (InvalidActionException | NoParkingSlotAvailable ex) {
            throw ex;
        } catch (Exception ex) {
            throw new FailedToExecuteAction(action, ex.getMessage());
        }
    }

    private void createParkingLot(int capacity) throws ParkingLotIsAlreadyCreated {
        if(isParkingLotInitiated()) {
            throw new ParkingLotIsAlreadyCreated();
        }
        this.parkingSlotsManager = new ParkingSlotsManager<>(capacity);
        System.out.println(Messages.parkingLotSuccessMessage(capacity));
    }

    private void parkVehicle(V vehicle) throws NoParkingSlotAvailable, ParkingLotIsNotInitiated, VehicleIsAlreadyParked {
        checkParkingLotInitiated();
        this.parkingSlotsManager.park(vehicle);
    }

    private void leaveVehicle(int slotNumber) throws InvalidActionException, ParkingLotIsNotInitiated {
        checkParkingLotInitiated();
        this.parkingSlotsManager.leave(slotNumber);
    }

    private void showCurrentStatus() throws ParkingLotIsNotInitiated {
        checkParkingLotInitiated();
        this.parkingSlotsManager.showStatus();
    }

    private void slotNumbersForColor(String color) throws ParkingLotIsNotInitiated {
        checkParkingLotInitiated();
        List<Integer> slotNums = this.parkingSlotsManager.getSlotNumbersForColor(color);
        if(slotNums == null || slotNums.isEmpty()) {
            System.out.println(Messages.NOT_FOUND);
            return;
        }
        String commaSeparatedResultStr = slotNums.stream().map(String::valueOf).collect(Collectors.joining(", "));
        System.out.println(commaSeparatedResultStr);
    }

    private void slotNumbersForRegNum(String regNum) throws ParkingLotIsNotInitiated {
        checkParkingLotInitiated();
        int slotNum = this.parkingSlotsManager.getSlotNumberForRegNum(regNum);
        if(slotNum == 0) {
            System.out.println(Messages.NOT_FOUND);
        } else {
            System.out.println(slotNum);
        }

    }

    private void regNumsForColor(String color) throws ParkingLotIsNotInitiated {
        checkParkingLotInitiated();
        List<String> regNums = this.parkingSlotsManager.getRegNumsForColor(color);
        if(regNums == null || regNums.isEmpty()) {
            System.out.println("No vehicle of color "+color+" is parked in the lot.");
            return;
        }
        String commaSeparatedResultStr = regNums.stream().collect(Collectors.joining(", "));
        System.out.println(commaSeparatedResultStr);
    }

    private void exit() {
        this.parkingSlotsManager = null;
        System.out.println("Exiting!");
    }

    private void checkParkingLotInitiated() throws ParkingLotIsNotInitiated {
        if(!isParkingLotInitiated()) {
            throw new ParkingLotIsNotInitiated();
        }
    }

    public boolean isParkingLotInitiated() {
        return this.parkingSlotsManager != null;
    }

    public int getRemaningCapacity() {
        if(!isParkingLotInitiated()) return 0;
        return this.parkingSlotsManager.getRemainingCapacity();
    }

}
