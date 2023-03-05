package main.java;

public class Truck extends Vehicle {

    private int numTrailers;

    /**
     * @param registration The truck's registration number
     * @param manufacturer The truck's manufacturer
     * @param numTrailers The truck's trailer capacity
     */
    public Truck(String registration, String manufacturer, int numTrailers) {

        super(registration, manufacturer);
        this.numTrailers = numTrailers;
    }

    public int calculateBasicTripCost() {

        if (this.numTrailers <= 1) {

            return 1250;

        } else {

            return 1500;
        }

    }

    public String toString() {
                                    
        return "Vehicle type: Van" +
        ", Registration: " + this.getRegistration() + 
        ", Manufacturer: " + this.getManufacturer() + 
        ", Trailer capacity: " + this.getNumTrailers();
    }

    public int getNumTrailers() {
        return numTrailers;
    }

}
