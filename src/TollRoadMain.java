import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
* By Anthony de Cruz
*
* Formatted with google-java-format-1.16.0
* > Enum used in CustomerAccount
* > Hashmap used as CustomerAccount collection
*/

public class TollRoadMain {

    public static void main(String[] args) {

        runTests();

        try {
            int profit = simulateFromFile(initialiseTollRoadFromFile());

            System.out.println("Total Profit: " + profit);

        } catch (IOException exception) {
            System.out.println("Something went wrong with the datasets:");
            exception.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create a TollRoad object from customerData.txt.
     * 
     * @return The initialised TollRoad
     * @throws IOException Thrown when the File or Scanner
     *                     have problems getting the data
     */
    public static TollRoad initialiseTollRoadFromFile() throws IOException {

        TollRoad tollRoad = new TollRoad();
        ArrayList<String> customerData = new ArrayList<>();

        try {
            customerData = readData("#", "customerData.txt");
        } catch (IOException exception) {
            throw exception;
        }

        /*
         * Break down entries into their 8 components
         * Format: <vehicleType>,<regNum>,<firstName>,
         * <lastName>,<vehicleInfo(manufacturer)>,
         * <vehicleInfo(specifications)>,<startingBalance>,<discountType>#
         */
        for (String customerEntry : customerData) {
            String[] values = customerEntry.split(",");
            Vehicle vehicle;

            switch (values[0]) {

                case "Car":
                    vehicle = new Car(values[1], values[4],
                            Integer.parseInt(values[5]));
                    break;

                case "Van":
                    vehicle = new Van(values[1], values[4],
                            Integer.parseInt(values[5]));
                    break;

                case "Truck":
                    vehicle = new Truck(values[1], values[4],
                            Integer.parseInt(values[5]));
                    break;

                default:
                    throw new IOException("Invalid vehicle type: "
                            + values[0]);

            }

            CustomerAccount customer = new CustomerAccount(values[2], values[3],
                    Integer.parseInt(values[6]), vehicle);

            switch (values[7]) {

                case "STAFF":
                    customer.activateStaffDiscount();
                    break;

                case "FRIENDS_AND_FAMILY":
                    customer.activateFriendsAndFamilyDiscount();
                    break;

                case "NONE":
                    break;

                default:
                    throw new IOException("Invalid discount type: "
                            + values[7]);
            }

            tollRoad.addCustomer(customer);
        }

        return tollRoad;
    }

    /**
     * Simulate the toll roads transactions from transactions.txt.
     * 
     * @param road The TollRoad object to be used
     * @return Total profit
     */
    public static int simulateFromFile(TollRoad road) throws IOException {

        ArrayList<String> transactionData = new ArrayList<>();

        try {
            transactionData = readData("\\$", "transactions.txt");
        } catch (IOException exception) {
            throw exception;
        }

        for (String transaction : transactionData) {

            String[] values = transaction.split(",");

            try {

                switch (values[0]) {

                    case ("addFunds"):
                        road.findCustomer(values[1]).addFunds(Integer.parseInt(values[2]));
                        System.out.println(values[1] + ": " + values[2] + " added successfully");
                        break;

                    case ("makeTrip"):
                        road.chargeCustomer(values[1]);
                        System.out.println(values[1] + ": Trip completed successfully");
                        break;
                }

            } catch (CustomerNotFoundException
                    | InsufficientAccountBalanceException
                    | IllegalArgumentException exception) {

                if (exception instanceof CustomerNotFoundException) {
                    System.out.println(values[1]
                            + ": makeTrip failed. CustomerAccount does not exist.");
                    continue;
                }

                if (exception instanceof InsufficientAccountBalanceException) {
                    System.out.println(values[1]
                            + ": makeTrip failed. Insufficient funds.");
                    continue;
                }

                if (exception instanceof IllegalArgumentException) {
                    System.out.println(values[1]
                            + ": makeTrip failed. Invalid amount of funds.");
                    continue;
                }
            }
        }

        return road.getMoneyMade();
    }

    /**
     * Reads file data entries and returns it as a list.
     * 
     * @param delimiter The entry regex delimiter
     * @param path      The file path
     * @return The list of entries
     * @throws IOException
     */
    private static ArrayList<String> readData(String delimiter, String path)
            throws IOException {

        try {
            Scanner scanner = new Scanner(new File(path));
            scanner.useDelimiter(delimiter);

            ArrayList<String> entries = new ArrayList<>();

            while (scanner.hasNext()) {
                entries.add(scanner.next());
            }
            return entries;

        } catch (IOException exception) {
            throw exception;
        }
    }

    /**
     * Run all unit tests.
     */
    private static void runTests() {

        // Each test returns whether or not it passed.
        boolean passed = true;

        passed = passed && Car.main();
        passed = passed && Truck.main();
        passed = passed && Van.main();
        passed = passed && CustomerAccount.main();
        passed = passed && TollRoad.main();

        if (passed) {
            System.out.println("<-- All tests passed :) -->\n");

        } else {
            System.out.println("<-- Test failed -->");
            System.exit(0);
        }
    }

}
