package com.gojek.parking.app.extractors;

import com.gojek.parking.app.actions.*;
import com.gojek.parking.app.exceptions.EmptyInputException;
import com.gojek.parking.app.exceptions.InvalidActionNameException;
import com.gojek.parking.app.exceptions.InvalidInputException;
import com.gojek.parking.app.model.Car;
import com.gojek.parking.app.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class InputExtractor {

    private static InputExtractor instance;

    private InputExtractor() {}

    public static InputExtractor getInstance() {
        if(instance == null)
            instance = new InputExtractor();
        return instance;
    }


    public List<Action> extractActions(List<String> inputCommands) {
        List<Action> actions = new ArrayList<>();
        for(String inputCommand: inputCommands) {
            try {
                actions.add(extractAction(inputCommand));
            } catch (InvalidInputException ex) {
                System.out.println("Failed to execute action "+inputCommand+" due to "+ex.getMessage());
            }

        }
        return actions;
    }


    public Action extractAction(String line) throws InvalidInputException {
        if(line == null || line.isEmpty())
            throw new EmptyInputException();

        StringTokenizer tokenizer = new StringTokenizer(line);
        String commandStr = extractString(tokenizer, line);
        ActionType actionType = ActionType.fromString(commandStr);

        Action action;

        switch (actionType) {
            case CREATE_PARKING_LOT:
                action = new ParkingLotCreation(extractInt(tokenizer, line));
                break;

            case PARK:
                action = extractParkingData(tokenizer, line);
                break;

            case LEAVE:
                action = extractLeavingData(tokenizer, line);
                break;

            case SHOW_CURRENT_STATUS:
                action = new ShowCurrentStatus();
                break;

            case SLOT_NUMBERS_FOR_COLOR:
                action = extractSlotNumsForColorData(tokenizer, line);
                break;

            case SLOT_NUMBER_FOR_REGISTRATION_NUM:
                action = extractSlotNumForRegNumData(tokenizer, line);
                break;

            case REGISTRATION_NUMS_FOR_COLOR:
                action = extractRegNumForColorData(tokenizer, line);
                break;

            case EXIT:
                action = new ExitAction();
                break;

            default:
                  throw new InvalidActionNameException(commandStr);
        }

        return action;
    }

    private RegistrationsNumsForColors extractRegNumForColorData(StringTokenizer tokenizer, String line) throws InvalidInputException {
        String color = extractString(tokenizer, line);
        return new RegistrationsNumsForColors(color);
    }

    private SlotNumForRegistrationNum extractSlotNumForRegNumData(StringTokenizer tokenizer, String line) throws InvalidInputException {
        String regNum =  extractString(tokenizer, line);
        return new SlotNumForRegistrationNum(regNum);
    }

    private SlotNumsForColor extractSlotNumsForColorData(StringTokenizer tokenizer, String line) throws InvalidInputException {
        String color = extractString(tokenizer, line);
        return new SlotNumsForColor(color);
    }

    private Leave extractLeavingData(StringTokenizer tokenizer, String line) throws InvalidInputException {
        int slotNum = extractInt(tokenizer, line);
        return new Leave(slotNum);
    }

    private Park<Vehicle> extractParkingData(StringTokenizer tokenizer, String line) throws InvalidInputException {
        String registrationNum = extractString(tokenizer, line);
        String color = extractString(tokenizer, line);
        Car car = new Car(color, registrationNum);
        return new Park<>(car);
    }

    private int extractInt(StringTokenizer tokenizer, String line) throws InvalidInputException {
        if(tokenizer == null || !tokenizer.hasMoreTokens())
            throw new InvalidInputException(line);

        int num;

        try {
            num = Integer.parseInt(tokenizer.nextToken());
        } catch (NumberFormatException ne) {
            throw new InvalidInputException(ne.getMessage());
        }
        return num;
    }



    private String extractString(StringTokenizer tokenizer, String line) throws InvalidInputException {
        if(tokenizer == null || !tokenizer.hasMoreTokens())
            throw new InvalidInputException(line);

        String str = tokenizer.nextToken();

        if(str == null || str.isEmpty())
            throw new InvalidInputException(line);

        return str;
    }




}
