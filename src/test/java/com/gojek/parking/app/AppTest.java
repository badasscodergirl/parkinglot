package com.gojek.parking.app;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.gojek.parking.app.actions.*;
import com.gojek.parking.app.constants.Messages;
import com.gojek.parking.app.exceptions.FailedToExecuteAction;
import com.gojek.parking.app.exceptions.NoParkingSlotAvailable;
import com.gojek.parking.app.exceptions.ParkingLotIsAlreadyCreated;
import com.gojek.parking.app.model.Car;
import com.gojek.parking.app.service.ParkingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void createParkingLotTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();
        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(10);
        parkingService.executeAction(parkingLotCreation);
        assertTrue(parkingService.isParkingLotInitiated());
    }

    @Test(expected = FailedToExecuteAction.class)
    public void parkingLotAlreadyExistsTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(10);
        parkingService.executeAction(parkingLotCreation);

        assertTrue(parkingService.isParkingLotInitiated());

        parkingService.executeAction(parkingLotCreation);
    }

    @Test(expected = FailedToExecuteAction.class)
    public void parkingLotDoesNotExistsTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        Car car1 = new Car("White", "KA-01-HH-1234");
        Park<Car> parkCar = new Park<>(car1);

        parkingService.executeAction(parkCar);

    }

    @Test
    public void parkTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(10);
        Car car1 = new Car("White", "KA-01-HH-1234");
        Park<Car> parkCar = new Park<>(car1);

        parkingService.executeAction(parkingLotCreation);
        assertTrue(parkingService.isParkingLotInitiated());

        assertEquals(10, parkingService.getRemaningCapacity());

        parkingService.executeAction(parkCar);
        assertEquals(9, parkingService.getRemaningCapacity());
    }

    @Test(expected = FailedToExecuteAction.class)
    public void parkTestWithoutLotCreationTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        Car car1 = new Car("White", "KA-01-HH-1234");
        Park<Car> parkCar = new Park<>(car1);

        parkingService.executeAction(parkCar);

    }


    @Test
    public void leaveTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(10);
        Car car1 = new Car("White", "KA-01-HH-1234");
        Park<Car> parkCar = new Park<>(car1);
        Leave leave1 = new Leave(1);

        parkingService.executeAction(parkingLotCreation);

        assertTrue(parkingService.isParkingLotInitiated());

        parkingService.executeAction(parkCar);
        assertEquals( 9, parkingService.getRemaningCapacity());

        parkingService.executeAction(leave1);
        assertEquals(10, parkingService.getRemaningCapacity());
    }

    @Test(expected = NoParkingSlotAvailable.class)
    public void parkingLotFullTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(2);

        Park<Car> park1 = new Park<>(new Car("White", "KA-01-HH-1111"));
        Park<Car> park2 = new Park<>(new Car("White", "KA-01-HH-2222"));
        Park<Car> park3 = new Park<>(new Car("White", "KA-01-HH-3333"));

        parkingService.executeAction(parkingLotCreation);
        assertEquals(2, parkingService.getRemaningCapacity());

        parkingService.executeAction(park1);
        assertEquals(1, parkingService.getRemaningCapacity());

        parkingService.executeAction(park2);
        assertEquals(0, parkingService.getRemaningCapacity());

        parkingService.executeAction(park3);
    }

    @Test(expected = FailedToExecuteAction.class)
    public void alreadyParkedTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(2);

        Park<Car> park1 = new Park<>(new Car("White", "KA-01-HH-1111"));

        parkingService.executeAction(parkingLotCreation);
        assertEquals(2, parkingService.getRemaningCapacity());

        parkingService.executeAction(park1);
        assertEquals(1, parkingService.getRemaningCapacity());

        parkingService.executeAction(park1);
    }

    @Test
    public void getSlotsForColorTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(10);

        Park<Car> park1 = new Park<>(new Car("White", "KA-01-HH-1111"));
        Park<Car> park2 =  new Park<>(new Car("Blue", "KA-01-HH-2222"));
        Park<Car> park3 =  new Park<>(new Car("Red", "KA-01-HH-3333"));
        Park<Car> park4 =  new Park<>(new Car("White", "KA-01-HH-4444"));
        Park<Car> park5 =  new Park<>(new Car("Brown", "KA-01-HH-5555"));
        Park<Car> park6 =  new Park<>(new Car("Blue", "KA-01-HH-6666"));

        parkingService.executeAction(parkingLotCreation);
        assertEquals(10, parkingService.getRemaningCapacity());

        parkingService.executeAction(park1);
        parkingService.executeAction(park2);
        parkingService.executeAction(park3);
        parkingService.executeAction(park4);
        parkingService.executeAction(park5);
        parkingService.executeAction(park6);

        parkingService.executeAction(new SlotNumsForColor("White"));
        assertThat(outContent.toString().trim(), containsString("1, 4"));

        parkingService.executeAction(new SlotNumsForColor("Blue"));
        assertThat(outContent.toString().trim(), containsString("2, 6"));
    }

    @Test
    public void getSlotForRegNumTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(10);

        Park<Car> park1 = new Park<>(new Car("White", "KA-01-HH-1111"));
        Park<Car> park2 =  new Park<>(new Car("Blue", "KA-01-HH-2222"));

        parkingService.executeAction(parkingLotCreation);
        assertEquals(10, parkingService.getRemaningCapacity());

        parkingService.executeAction(park1);
        parkingService.executeAction(park2);

        parkingService.executeAction(new SlotNumForRegistrationNum("KA-01-HH-1111"));
        assertThat(outContent.toString().trim(), endsWith("1"));

        parkingService.executeAction(new SlotNumForRegistrationNum("KA-01-HH-1234"));
        assertThat(outContent.toString().trim(), endsWith(Messages.NOT_FOUND));
    }

    @Test
    public void getRegNumForColorsTest() throws Exception {
        ParkingService<Car> parkingService = new ParkingService<>();

        ParkingLotCreation parkingLotCreation = new ParkingLotCreation(10);

        Park<Car> park1 = new Park<>(new Car("White", "KA-01-HH-1111"));
        Park<Car> park2 =  new Park<>(new Car("Blue", "KA-01-HH-2222"));
        Park<Car> park3 =  new Park<>(new Car("Red", "KA-01-HH-3333"));
        Park<Car> park4 =  new Park<>(new Car("White", "KA-01-HH-4444"));
        Park<Car> park5 =  new Park<>(new Car("Brown", "KA-01-HH-5555"));
        Park<Car> park6 =  new Park<>(new Car("Blue", "KA-01-HH-6666"));

        parkingService.executeAction(parkingLotCreation);
        assertEquals(10, parkingService.getRemaningCapacity());

        parkingService.executeAction(park1);
        parkingService.executeAction(park2);
        parkingService.executeAction(park3);
        parkingService.executeAction(park4);
        parkingService.executeAction(park5);
        parkingService.executeAction(park6);

        parkingService.executeAction(new RegistrationsNumsForColors("White"));
        assertThat(outContent.toString().trim(), endsWith("KA-01-HH-1111, KA-01-HH-4444"));

        parkingService.executeAction(new RegistrationsNumsForColors("Blue"));
        assertThat(outContent.toString().trim(), endsWith("KA-01-HH-2222, KA-01-HH-6666"));
    }


}
