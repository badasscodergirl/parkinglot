package com.gojek.parking.app;

import com.gojek.parking.app.actions.*;
import com.gojek.parking.app.exceptions.*;
import com.gojek.parking.app.extractors.InputExtractor;
import com.gojek.parking.app.model.Car;
import com.gojek.parking.app.service.InputService;
import com.gojek.parking.app.service.ParkingService;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    ParkingService<Car> parkingService;
    InputService inputService;
    InputExtractor inputExtractor;

    public static void main(String[] args) {
        System.out.println("Hello !");
        App app = new App();
        app.init();
        app.startService(args);
    }

    void init() {
        this.parkingService = new ParkingService<>();
        this.inputService = InputService.getInstance();
        this.inputExtractor = InputExtractor.getInstance();
    }

    void startService(String[] args) {
        if(args.length == 0) {
            String line;
            while (true) {
                line = inputService.readLine();
                try {
                    Action action = this.inputExtractor.extractAction(line);

                    executeCommand(action);
                    if(ActionType.EXIT.equals(action.actionType())) {
                        inputService.closeReader();
                        break;
                    }
                } catch (EmptyInputException ex) {
                    continue;
                } catch (InvalidInputException ex) {
                    System.out.println("Invalid input "+ex.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                executeFileInput(args[0]);
            } catch (IOException ex) {
                System.out.println("Error occurred while reading file "+args[0]+" Error details="+ex.getMessage());
            }
        }
    }

    void executeFileInput(String fileName) throws IOException {
        List<String> inputCommands = inputService.readFileInput(fileName);
        List<Action> inputActions = inputExtractor.extractActions(inputCommands);
        this.parkingService.executeActions(inputActions);
    }

    void executeCommand(Action action) {
        try {
            this.parkingService.executeAction(action);
        } catch (NoParkingSlotAvailable ex) {
            System.out.println("Sorry, parking lot is full");
        } catch (InvalidActionException | FailedToExecuteAction ex) {
            System.out.println("Invalid input was sent. "+ex.getMessage());
        }
    }
}
