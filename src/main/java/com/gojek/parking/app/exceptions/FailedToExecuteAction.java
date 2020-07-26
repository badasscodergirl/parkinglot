package com.gojek.parking.app.exceptions;

import com.gojek.parking.app.actions.Action;

public class FailedToExecuteAction extends Exception {

    public FailedToExecuteAction(Action action, String reason) {
        super("Failed to execute action "+action.actionType().getActionName()+" due to "+reason);
    }
}
