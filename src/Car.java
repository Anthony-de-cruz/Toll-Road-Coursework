import java.util.HashMap;

public class Car extends Vehicle {

    private int numberOfSeats;

    /**
     * @param registration  The car's registration number
     * @param manufacturer  The car's manufacturer
     * @param numberOfSeats The number of seats in the car (atleast 1)
     */
    public Car(String registration, String manufacturer, int numberOfSeats) throws IllegalArgumentException {

        super(registration, manufacturer);

        if (numberOfSeats < 1) {
            throw new IllegalArgumentException("Cannot have zero or less seats.");
        }

        this.numberOfSeats = numberOfSeats;
    }

    public int calculateBasicTripCost() {

        if (this.numberOfSeats < 6) {

            return 500;

        } else {

            return 600;
        }
    }

    public String toString() {

        return "Vehicle type: Car" +
                ", Registration: " + this.getRegistration() +
                ", Manufacturer: " + this.getManufacturer() +
                ", Number of seats: " + this.getNumberOfSeats();
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
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

            // Car, expected trip cost
            HashMap<Car, Integer> validCars = new HashMap<Car, Integer>();

            validCars.put(new Car("G22KLR", "Honda", 4), 500);
            validCars.put(new Car("G23KLR", "Tiny", 1), 500);
            validCars.put(new Car("G24KLR", "Vauxhall", 8), 600);

            // Car.calculateBasicTripCost()
            for (Car car : validCars.keySet()) {
                if (car.calculateBasicTripCost() != validCars.get(car)) {

                    System.out.println("Valid case: FAILED: Incorrect basic trip cost calculation.");
                    return false;
                }
            }

        } catch (IllegalArgumentException exception) {

            exception.printStackTrace();

            if (exception instanceof IllegalArgumentException) {
                System.out.println("Valid case: FAILED: Valid number of seats threw IllegalArgumentException.");
            } else {
                System.out.println("Valid case: FAILED");
            }

            return false;
        }

        // Invalid test
        try {

            Car invalidCar = new Car("eee", "Vauxhall", -2);

            invalidCar.calculateBasicTripCost();

            System.out.println("Invalid case: FAILED: Exception not thrown when seats were below 1.");
            return false;

        } catch (IllegalArgumentException exception) {

        }

        System.out.println("Car test: PASSED");
        return true;
    }

}
