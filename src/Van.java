import java.util.HashMap;

public class Van extends Vehicle {

    private int payload;

    /**
     * @param registration The van's registration number
     * @param manufacturer The van's manufacturer
     * @param payload      The van's cargo capacity in Kg
     */
    public Van(String registration, String manufacturer, int payload) throws IllegalArgumentException {

        super(registration, manufacturer);

        if (payload < 0) {
            throw new IllegalArgumentException("Cannot have a negative payload");
        }
        this.payload = payload;

    }

    public int calculateBasicTripCost() {

        if (this.payload <= 600) {

            return 500;

        } else if (this.payload <= 800) {

            return 750;

        } else {

            return 1000;
        }
    }

    @Override
    public String toString() {

        return "Vehicle type: Van" +
                ", Registration: " + this.getRegistration() +
                ", Manufacturer: " + this.getManufacturer() +
                ", Payload: " + this.getPayload();
    }

    public int getPayload() {
        return payload;
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

            // Van, expected trip cost
            HashMap<Van, Integer> validVans = new HashMap<Van, Integer>();

            validVans.put(new Van("EEB52", "Mercedes", 550), 500);
            validVans.put(new Van("EEB53", "Ford", 750), 750);
            validVans.put(new Van("EEB54", "Chadcars", 850), 1000);

            // Van.calculateBasicTripCost()
            for (Van Van : validVans.keySet()) {
                if (Van.calculateBasicTripCost() != validVans.get(Van)) {

                    System.out.println("Valid case: FAILED: Incorrect basic trip cost calculation.");
                    return false;
                }
            }

        } catch (IllegalArgumentException exception) {

            exception.printStackTrace();

            if (exception instanceof IllegalArgumentException) {
                System.out.println("Valid case: FAILED: Valid payload threw IllegalArgumentException.");
            } else {
                System.out.println("Valid case: FAILED");
            }

            return false;
        }

        // Invalid test
        try {

            Van invalidVan = new Van("B4D", "Vano", -10);

            invalidVan.calculateBasicTripCost();

            System.out.println("Invalid case: FAILED: Exception not thrown when payload was below 0.");
            return false;

        } catch (IllegalArgumentException exception) {

        }

        System.out.println("Van test: PASSED");
        return true;
    }

}
