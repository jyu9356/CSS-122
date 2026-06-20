// Jeffrey Yu
// 05/24/2026
// CSE 122 
// P3: Program Linting
// TA: Colin Lim

// This class contains JUnit tests for the AirTrafficControl class.
// It tests the behavior of takeOff(), distributeFuel(), refuelIdleAircraft(),
// findIdleAircraft(), and saveQueue() methods to identify bugs in the implementation.

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ATCTest {
    private AirTrafficControl atc;
    private Aircraft ac1;
    private Aircraft ac2;
    
    @BeforeEach
    public void setup() {
        atc = new AirTrafficControl();
        ac1 = new Aircraft(5, 15, "Tokyo");
        ac2 = new Aircraft(40, 15, "Beijing");
    }

    @Test
    @DisplayName("Example Test")
    public void exampleTest() {
        assertTrue(true);
    }

    // B - Tests that takeOff() removes the aircraft at the front of the queue
    @Test
    @DisplayName("takeOff() removes aircraft from front of queue")
    public void takeOffTest() {
        Aircraft ac = new Aircraft(20, 15, "Singapore");
        atc.addIdleAircraft(1, ac);
        atc.queueAircraft(1);
        atc.takeOff();
        assertTrue(atc.getTakeoffQueue().isEmpty());
    }

    // B - Tests that takeOff() throws an exception when the queue is empty
    // E - IllegalStateException if the takeoff queue is empty
    @Test
    @DisplayName("takeOff() throws IllegalStateException if queue empty")
    public void takeOffExceptionTest() {
        assertThrows(IllegalStateException.class, () -> {atc.takeOff();});
    }

    // B - Tests that distributeFuel() correctly updates fuel levels of idle aircraft
    @Test
    @DisplayName("distributeFuel() adds fuel to underfueled aircraft until = takeoff fuel")
    public void distributeFuelTest() {
        Aircraft ac1 = new Aircraft(20, 15, "Singapore"); 
        Aircraft ac2 = new Aircraft(20, 40, "London");   
        atc.addIdleAircraft(1, ac1);
        atc.addIdleAircraft(2, ac2);
        atc.distributeFuel(); 
        assertEquals(20, atc.getIdleAircraft().get(1).getFuel());
        assertEquals(40, atc.getIdleAircraft().get(2).getFuel());
    }       

    // B - Tests that refuelIdleAircraft() adds the correct amount of fuel to all idle aircraft
    // P - fuelAmount: the amount of fuel in tons to add to each idle aircraft
    @Test
    @DisplayName("refuelIdleAircraft() adds fuel to all idle aircraft")
    public void refuelIdleAircraftTest() {
        Aircraft ac1 = new Aircraft(20, 15, "Singapore"); 
        Aircraft ac2 = new Aircraft(20, 40, "London");   
        Aircraft ac3 = new Aircraft(15, 30, "Berlin");  
        atc.addIdleAircraft(1, ac1);
        atc.addIdleAircraft(2, ac2);
        atc.addIdleAircraft(3, ac3);
        atc.refuelIdleAircraft(25);
        assertEquals(45, atc.getIdleAircraft().get(1).getFuel());
        assertEquals(45, atc.getIdleAircraft().get(2).getFuel());
        assertEquals(40, atc.getIdleAircraft().get(3).getFuel());  
    }

    // B - Tests that refuelIdleAircraft() does not exceed an aircraft's fuel capacity
    // P - fuelAmount: the amount of fuel in tons to add to each idle aircraft
    @Test
    @DisplayName("refuelIdleAircraft() caps fuel at capacity")
    public void refuelIdleAircraftCapTest() {
        Aircraft ac1 = new Aircraft(70, 15, "Singapore"); // 70 + 40 would exceed 100
        atc.addIdleAircraft(1, ac1);
        atc.refuelIdleAircraft(40);
        assertEquals(100, atc.getIdleAircraft().get(1).getFuel()); 
    }

    // B - Tests that refuelIdleAircraft() throws an exception for negative fuel input
    // E - IllegalArgumentException if fuelAmount is negative
    // P - fuelAmount: the amount of fuel in tons to add to each idle aircraft
    @Test
    @DisplayName("refuelIdleAircraft() throws IllegalArgumentException for negative fuel")
    public void refuelIdleAircraftNegativeTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            atc.refuelIdleAircraft(-10);
        });
    }

    // B - Tests that findIdleAircraft() returns the correct set of aircraft for a destination
    // R - Set of all idle aircraft flying to the given destination
    // P - destination: the destination to search for among idle aircraft
    @Test
    @DisplayName("findIdleAircraft() returns all aircraft flying to destination")
    public void findIdleAircraftTest() {
        Set<Aircraft> expectedAircraft = new HashSet<>();
        Aircraft ac1 = new Aircraft(20, 15, "Singapore"); 
        Aircraft ac2 = new Aircraft(20, 40, "London");   
        Aircraft ac3 = new Aircraft(15, 30, "Singapore");  
        atc.addIdleAircraft(1, ac1);
        atc.addIdleAircraft(2, ac2);
        atc.addIdleAircraft(3, ac3);
        Set<Aircraft> foundAircraft = atc.findIdleAircraft("Singapore");
        expectedAircraft.add(ac1);
        expectedAircraft.add(ac3);
        assertEquals(expectedAircraft, foundAircraft);
    }

    // B - Tests that findIdleAircraft() returns an empty set when no aircraft match
    // R - Empty set when no idle aircraft are flying to the given destination
    // P - destination: the destination to search for among idle aircraft
    @Test
    @DisplayName("findIdleAircraft() returns empty set when no aircraft match destination")
    public void findIdleAircraftNonMatchTest() {
        Aircraft ac1 = new Aircraft(20, 15, "Singapore"); 
        Aircraft ac2 = new Aircraft(20, 40, "London");   
        Aircraft ac3 = new Aircraft(15, 30, "Singapore");  
        atc.addIdleAircraft(1, ac1);
        atc.addIdleAircraft(2, ac2);
        atc.addIdleAircraft(3, ac3);
        Set<Aircraft> foundAircraft = atc.findIdleAircraft("Jakarta");
        assertTrue(foundAircraft.isEmpty());
    }

    // B - Tests that saveQueue() writes all queued aircraft to a file in correct order
    // E - FileNotFoundException if the file cannot be found or created
    // P - fileName: the name of the file to save the takeoff queue to
    @Test
    @DisplayName("saveQueue() writes aircraft to file in correct order")
    public void saveQueueTest() throws FileNotFoundException {
        Aircraft ac1 = new Aircraft(18, 14, "Paris");
        Aircraft ac2 = new Aircraft(15, 9, "Dubai");
        atc.addIdleAircraft(1, ac1);
        atc.addIdleAircraft(2, ac2);
        atc.queueAircraft(1);
        atc.queueAircraft(2);
        atc.saveQueue("test.txt");
        Scanner sc = new Scanner(new File("test.txt"));
        assertEquals("destination: Paris, fuel: 18, takeoff fuel: 14, fuel capacity: 100", sc.nextLine());
        assertEquals("destination: Dubai, fuel: 15, takeoff fuel: 9, fuel capacity: 100", sc.nextLine());
    }

    // B - Tests that saveQueue() leaves the takeoff queue unchanged after saving
    // E - FileNotFoundException if the file cannot be found or created
    // P - fileName: the name of the file to save the takeoff queue to
    @Test
    @DisplayName("saveQueue() does not modify the takeoff queue")
    public void saveQueueUnchangedTest() throws FileNotFoundException {
        Aircraft ac1 = new Aircraft(18, 14, "Bangkok");
        Aircraft ac2 = new Aircraft(15, 9, "Edinburgh");
        atc.addIdleAircraft(1, ac1);
        atc.addIdleAircraft(2, ac2);
        atc.queueAircraft(1);
        atc.queueAircraft(2);
        Queue<Aircraft> beforeQueue = atc.getTakeoffQueue();
        atc.saveQueue("test.txt");
        assertEquals(beforeQueue, atc.getTakeoffQueue());
    }
}
