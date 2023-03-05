import java.util.HashMap;

public class Truck extends Vehicle {

    private int numTrailers;

    /**
     * @param registration The truck's registration number
     * @param manufacturer The truck's manufacturer
     * @param numTrailers  The truck's trailer capacity
     */
    public Truck(String registration, String manufacturer, int numTrailers) throws IllegalArgumentException {

        super(registration, manufacturer);

        if (numTrailers < 1) {
            throw new IllegalArgumentException("Cannot have zero or negative trailer capacity.");
        }

        this.numTrailers = numTrailers;
    }

    public int calculateBasicTripCost() {

        if (this.numTrailers <= 1) {

            return 1250;

        } else {

            return 1500;
        }

    }

    @Override
    public String toString() {

        return "Vehicle type: Van" +
                ", Registration: " + this.getRegistration() +
                ", Manufacturer: " + this.getManufacturer() +
                ", Trailer capacity: " + this.getNumTrailers();
    }

    public int getNumTrailers() {
        return numTrailers;
    }

    /* -------------------------------- Unit test ------------------------------- */

    /**
     * Test harness
     * 
     * @param args
     * @return Whether or the class passed the test
     */
    public static boolean main() {

        // Valid test
        try {

            // Truck, expected trip cost
            HashMap<Truck, Integer> validTrucks = new HashMap<Truck, Integer>();

            validTrucks.put(new Truck("BBMME2", "Ford", 3), 1500);
            validTrucks.put(new Truck("BBMME3", "Chevrolet", 1), 1250);
            validTrucks.put(new Truck("BBMME4", "Chungus", 5), 1500);

            // Truck.calculateBasicTripCost()
            for (Truck Truck : validTrucks.keySet()) {
                if (Truck.calculateBasicTripCost() != validTrucks.get(Truck)) {

                    System.out.println("Valid case: FAILED: Incorrect basic trip cost calculation.");
                    return false;
                }
            }

        } catch (IllegalArgumentException exception) {

            exception.printStackTrace();

            if (exception instanceof IllegalArgumentException) {
                System.out.println("Valid case: FAILED: Valid trailer capacity threw IllegalArgumentException.");
            } else {
                System.out.println("Valid case: FAILED");
            }

            return false;
        }

        // Invalid test
        try {

            Truck invalidTruck = new Truck("weee", "Wooo", -10);

            invalidTruck.calculateBasicTripCost();

            System.out.println("Invalid case: FAILED: Exception not thrown when trailer capacity were below 1.");
            return false;

        } catch (IllegalArgumentException exception) {

        }

        System.out.println("Truck test: PASSED");
        return true;
    }

}
