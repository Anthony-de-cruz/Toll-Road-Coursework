import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The class to represent the toll road.
 */
public class TollRoad {

    // I used a hash map here as it provides practically O(1) access times.
    private HashMap<String, CustomerAccount> customerAccounts;
    private int moneyMade;

    public TollRoad() {

        this.customerAccounts = new HashMap<>();
        this.moneyMade = 0;
    }

    /**
     * Add a customer to the collection.
     * 
     * @param acc The customer object.
     */
    public void addCustomer(CustomerAccount acc) {

        this.customerAccounts.put(acc.getVehicle().getRegistration(), acc);
    }

    /**
     * Returns the customer with the passed registration.
     * 
     * @param regNum The registration number to look up.
     * @return The found customer account.
     * @throws CustomerNotFoundException Thrown when the customer account is not
     *                                   found.
     */
    public CustomerAccount findCustomer(String regNum)
            throws CustomerNotFoundException {

        CustomerAccount customer = this.customerAccounts.get(regNum);

        if (Objects.isNull(customer)) {
            throw new CustomerNotFoundException(regNum + " Not found.");
        }
        return customerAccounts.get(regNum);
    }

    /**
     * Method to charge the customer. Calls CustomerAccount.makeTrip()
     * 
     * @param regNum
     * @throws InsufficientAccountBalanceException
     * @throws CustomerNotFoundException
     */
    public void chargeCustomer(String regNum)
            throws InsufficientAccountBalanceException,
            CustomerNotFoundException {

        CustomerAccount customer = this.findCustomer(regNum);
        this.moneyMade += customer.makeTrip();
    }

    public Map<String, CustomerAccount> getCustomerAccounts() {
        return this.customerAccounts;
    }

    public int getMoneyMade() {
        return this.moneyMade;
    }

    /* --------------------------- Unit test -------------------------- */
    /**
     * Test harness.
     * 
     * @return Whether or not the class passed the test
     */
    public static boolean main() {

        CustomerAccount[] customers = {
                new CustomerAccount("Henry", "Epp", 20000,
                        new Car("123EJ", "Citroen", 4)),
                new CustomerAccount("Barry", "Buzz", 10000,
                        new Car("H3EK2L", "Honda", 5)),
                new CustomerAccount("Ben", "Odd", 20300,
                        new Van("ERR", "Mercedes", 600)),
                new CustomerAccount("Jesse", "Murica", 23100,
                        new Truck("BARN2", "Dodge", 2)) };

        int expectedProfit = 3000;

        /* -------------------------- Valid tests ------------------------- */
        try {

            TollRoad validTollRoad = new TollRoad();

            // Add customers
            for (CustomerAccount customer : customers) {
                validTollRoad.addCustomer(customer);
            }

            // Search for customers
            for (CustomerAccount customer : customers) {

                if (validTollRoad.findCustomer(
                        customer.getVehicle().getRegistration()) != customer) {
                    System.out.println("TollRoad test: FAILED: "
                            + " findCustomer() did not return the correct customer.");
                    return false;
                }
            }

            // Charge customers
            for (String registration : validTollRoad.getCustomerAccounts()
                    .keySet()) {
                validTollRoad.chargeCustomer(registration);
            }

            // Check that the final profit is correct
            if (validTollRoad.getMoneyMade() != expectedProfit) {
                System.out.println("TollRoad test: FAILED: "
                        + " Incorrect profit. Expected: " + expectedProfit
                        + ", got: " + validTollRoad.getMoneyMade());
                return false;
            }

        } catch (CustomerNotFoundException
                | InsufficientAccountBalanceException exception) {

            if (exception instanceof CustomerNotFoundException) {
                System.out.println("TollRoad test: FAILED: "
                        + " CustomerNotFoundException thrown during valid test.");
                return false;
            }

            if (exception instanceof InsufficientAccountBalanceException) {
                System.out.println("TollRoad test: FAILED: "
                        + "InsufficientAccountBalanceException "
                        + " thrown during valid test.");
                return false;
            }
        }

        /* ------------------------- Invalid test ------------------------- */
        // Test findCustomer()
        try {
            TollRoad invalidTollRoad = new TollRoad();

            // Add customer
            invalidTollRoad.addCustomer(new CustomerAccount("Amy", "AAAAAAA", 10,
                    new Van("AAAA", "Mercedes", 900)));

            System.out.println(invalidTollRoad.findCustomer("AAAABa"));

            System.out.println("Invalid test: FAILED: "
                    + "CustomerNotFoundException "
                    + "not thrown when it was supposed to.");
            return false;

        } catch (CustomerNotFoundException exception) {
            // Intended to be thrown
        }

        // Test chargeCustomer()
        try {
            TollRoad invalidTollRoad = new TollRoad();

            // Add extra customer without any money
            invalidTollRoad.addCustomer(new CustomerAccount("Amy", "AAAAAAA", 10,
                    new Van("AAAA", "Mercedes", 900)));

            invalidTollRoad.chargeCustomer("AAAA");

            System.out.println("Invalid test: FAILED: "
                    + "InsufficientAccountBalanceException "
                    + "not thrown when it was supposed to.");
            return false;

        } catch (CustomerNotFoundException | InsufficientAccountBalanceException exception) {

            if (exception instanceof CustomerNotFoundException) {
                System.out.println("Invalid test: FAILED: Wrong exception thrown.");
                return false;
            }
        }

        System.out.println("TollRoad test: PASSED");
        return true;
    }

}
