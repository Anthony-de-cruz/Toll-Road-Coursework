package main.java;

/**
 * An abstract class to be extended by different types of vehicles, such as cars
 * or vans.
 */
public abstract class Vehicle {

    private String registration;
    private String manufacturer;

    /**
     * Standard vehicle constructor.
     * 
     * @param registration The vehicle's registration number
     * @param manufacturer The vehicle's manufacturer
     */
    public Vehicle(String registration, String manufacturer) {

        this.registration = registration;
        this.manufacturer = manufacturer;

    }

    /**
     * Calculates and returns the basic trip cost
     * 
     * @return The cost in pence
     */
    public abstract int calculateBasicTripCost();

    public String getRegistration() {
        return this.registration;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

}
