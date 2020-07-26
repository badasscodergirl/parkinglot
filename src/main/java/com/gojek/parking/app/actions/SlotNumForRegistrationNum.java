package com.gojek.parking.app.actions;

public class SlotNumForRegistrationNum extends Action {
    private String regNum;

    public SlotNumForRegistrationNum(String regNum) {
        super(ActionType.SLOT_NUMBER_FOR_REGISTRATION_NUM);
        this.regNum = regNum;
    }

    public String getRegistrationNum() {
        return this.regNum;
    }
}
