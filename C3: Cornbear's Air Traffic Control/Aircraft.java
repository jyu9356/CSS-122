// The following is the Aircraft class provided for your convenience in completing
// this assignment. DO NOT MODIFY this file, as it is just for reference!

// Represents a aircraft with a specific destination and fuel quantity. Aircrafts have
// a certain fuel needed for takeoff and fuel capacity.
public class Aircraft {
    public static final String CLASS_NAME = "Aircraft";
    public static final int FUEL_CAPACITY = 100;
    private int fuelLevel;
    private int takeoffFuel;
    private String destination;

    // Initializes an Aircraft with the passed in fuel level, 
    // takeoff fuel, and destination. Throws an IllegalArgumentException if the
    // passed in fuel level is negative or larger than FUEL_CAPACITY. Throws an
    // IllegalArgumentException if takeoffFuel is negative.
    public Aircraft(int fuelLevel, int takeoffFuel, String destination) {
        setFuel(fuelLevel);
        this.takeoffFuel = takeoffFuel;
        this.destination = destination;
    }
    
    // Returns the aircraft's current destination.
    public String getDestination() {
        return destination;
    }

    // Returns the amount of fuel currently contained in this aircraft. Valid fuel
    // amounts range from 0 to the fuel capacity of this aircraft
    public int getFuel() {
        return fuelLevel;
    }

    // Returns the amount of fuel needed for takeoff.
    // This will always be less than the fuel capacity.
    public int getTakeoffFuel() {
        return takeoffFuel;
    }

    // Returns the aircraft's fuel capacity.
    public int getFuelCapacity() {
        return FUEL_CAPACITY;
    }

    // Sets the amount of fuel currently in the aircraft to be newFuel. 
    // Throws an IllegalStateException if the passed in fuel level is 
    // negative or larger than FUEL_CAPACITY.
    public void setFuel(int newFuel) {
        if (newFuel < 0) {
            throw new IllegalArgumentException("Cannot set fuel for " + CLASS_NAME
                                               + " to a negative quantity!");
        }
        if (newFuel > FUEL_CAPACITY) {
            throw new IllegalArgumentException("Cannot set fuel for " + CLASS_NAME
                                               + " over capacity " + FUEL_CAPACITY + "!");
        }
        fuelLevel = newFuel;
    }

    // Returns a copy of this aircraft.
    public Aircraft clone() {
        return new Aircraft(fuelLevel, takeoffFuel, destination);
    }
    
	// Returns the standardized format of an aircraft in the following structure
	// destination: String, fuel: int, takeoff fuel: int, fuel capacity: global final int
    public String toString(){
        return "destination: " + this.destination + ", fuel: " + this.fuelLevel + ", takeoff fuel: "
                + this.takeoffFuel + ", fuel capacity: " + this.FUEL_CAPACITY;
    }

    // Checks equality based on fuel level, takeoff fuel, and destination.
    public boolean equals(Object otherObject) {
        if (otherObject == this) {
            return true;
        } else if (otherObject instanceof Aircraft) {
            Aircraft other = (Aircraft)otherObject;
            return this.fuelLevel == other.fuelLevel
                   && this.takeoffFuel == other.takeoffFuel
                   && this.destination.equals(other.destination); 
        } else {
            return false;
        }
    }

    // Hashes by class type, takeoff fuel, and destination.
    public int hashCode() {
        return CLASS_NAME.hashCode() + takeoffFuel * 37 + destination.hashCode() * 37 * 37;
    }
}
