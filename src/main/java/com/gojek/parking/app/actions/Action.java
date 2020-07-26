package com.gojek.parking.app.actions;

public abstract class Action {

    private ActionType type;

    public Action(ActionType action) {
        this.type = action;
    }

    public ActionType actionType() {
        return this.type;
    }
}
