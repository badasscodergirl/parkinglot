package com.gojek.parking.app.actions;

import com.gojek.parking.app.exceptions.InvalidActionNameException;

public enum ActionType {

    CREATE_PARKING_LOT("create_parking_lot"),
    PARK("park"),
    LEAVE("leave"),
    SHOW_CURRENT_STATUS("status"),
    SLOT_NUMBERS_FOR_COLOR("slot_numbers_for_cars_with_colour"),
    SLOT_NUMBER_FOR_REGISTRATION_NUM("slot_number_for_registration_number"),
    REGISTRATION_NUMS_FOR_COLOR("registration_numbers_for_cars_with_colour"),
    EXIT("exit");


    private String actionName;
    ActionType(String str) {
       this.actionName = str;
    }

    public String getActionName() {
        return this.actionName;
    }

    public static ActionType fromString(String str) throws InvalidActionNameException {
        for(ActionType actionType: ActionType.values()) {
            if(actionType.actionName.equalsIgnoreCase(str))
                return actionType;
        }
        throw new InvalidActionNameException(str);
    }

}
