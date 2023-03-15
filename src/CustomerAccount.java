import java.util.HashMap;

/**
 * Customer account class to represent individual customers.
 */
public class CustomerAccount implements Comparable<CustomerAccount> {

    private enum Discounts {
        STAFF, FRIENDS_AND_FAMILY, NONE
    }

    private String firstName;
    private String lastName;
    private int balance;
    private Vehicle vehicle;
    private Discounts discount;

    /**
     * No initial discount.
     * 
     * @param firstName Customer's first name
     * @param lastName  Customer's last name
     * @param balance   Customer's initial balance
     * @param vehicle   The vehicle associated with the customer
     */
    public CustomerAccount(String firstName, String lastName, int balance,
            Vehicle vehicle) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.vehicle = vehicle;
        this.discount = Discounts.NONE;
    }

    /**
     * Set the discount type of this account to be staff.
     */
    public void activateStaffDiscount() {
        this.discount = Discounts.STAFF;
    }

    /**
     * Set the discoutn type of this account to be friends and family.
     */
    public void activateFriendsAndFamilyDiscount() {
        this.discount = Discounts.FRIENDS_AND_FAMILY;
    }

    /**
     * Remove any active discount on the account.
     */
    public void deactivateDiscount() {
        this.discount = Discounts.NONE;
    }

    /**
     * Exists to centralise the application of discounts for consistency
     * 
     * @param value The value be deducted from
     * @return New value
     */
    private int calculateDiscount(int value) {

        if (this.discount == Discounts.STAFF) {
            value = (int) Math.floor(value * 0.5d);

        } else if (this.discount == Discounts.FRIENDS_AND_FAMILY) {
            value = (int) Math.floor(value * 0.9d);
        }

        return value;
    }

    /**
     * Add credit to the account balance.
     * 
     * @param amount Positive amount of credits to be added in pence
     * @throws IllegalArgumentException Thrown when negative pence are added
     */
    public void addFunds(int amount) throws IllegalArgumentException {

        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative funds.");
        }

        this.balance += amount;
    }

    /**
     * Calculate the customers basic trip cost and deduct the funds from their
     * account.
     * 
     * @return The amount charged
     * @throws InsufficientAccountBalanceException
     */
    public int makeTrip() throws InsufficientAccountBalanceException {

        int toll = this.vehicle.calculateBasicTripCost();
        toll = this.calculateDiscount(toll);

        if (toll > this.balance) {
            throw new InsufficientAccountBalanceException(
                    "Customer lacks the sufficient "
                            + "funds to complete transaction.");
        }

        this.balance -= toll;

        return toll;
    }

    @Override
    /**
     * Compare registration alphabetical order.
     * 
     * @param customerAccount The account to compare to
     * @return Comparison integer
     */
    public int compareTo(CustomerAccount customerAccount) {

        return this.getVehicle().getRegistration()
                .compareTo(customerAccount.getVehicle().getRegistration());
    }

    @Override
    public String toString() {
        return this.firstName + ", " + this.lastName + ", " + this.balance
                + ", " + this.vehicle.toString() + ", " + this.discount + "\n";
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getBalance() {
        return this.balance;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public Discounts getDiscount() {
        return this.discount;
    }

    /* --------------------------- Unit test -------------------------- */
    /**
     * Test harness.
     * 
     * @return Whether or the class passed the test
     */
    public static boolean main() {

        /* -------------------------- Valid tests ------------------------- */
        // A collection of customer accounts with some funds to add and the
        // expected
        // balance after a trip.
        try {

            // (CustomerAccount, funds to add, final funds expected)
            HashMap<CustomerAccount, int[]> validCstmr = new HashMap<>();

            validCstmr.put(new CustomerAccount("Jimmy", "Jones", 1500,
                    new Car("ee", "Honda", 5)), new int[] { 0, 1000 });
            validCstmr.put(new CustomerAccount("Bob", "Bones", 2400,
                    new Car("eee", "Bonda", 8)), new int[] { 200, 2000 });
            validCstmr.put(
                    new CustomerAccount("Bobby", "Rones", 2000,
                            new Van("eeee", "Bondaee", 1000)),
                    new int[] { 1000, 2000 });

            for (CustomerAccount customerAccount : validCstmr.keySet()) {

                customerAccount.addFunds(validCstmr.get(customerAccount)[0]);
                customerAccount.makeTrip();

                if (customerAccount
                        .getBalance() != validCstmr.get(customerAccount)[1]) {

                    System.out.println(
                            "Expected: " + validCstmr.get(customerAccount)[1]
                                    + ", got: " + customerAccount.getBalance());
                    System.out.println("Valid case: FAILED: "
                            + "Final balance was not the expected value.");
                    return false;
                }
            }

        } catch (IllegalArgumentException
                | InsufficientAccountBalanceException exception) {

            if (exception instanceof IllegalArgumentException) {

                System.out.println(
                        "Valid case: FAILED: Illegal argument passed on valid test.");
                return false;

            } else if (exception instanceof InsufficientAccountBalanceException) {

                System.out.println(
                        "Valid case: FAILED: Insufficient funds for transaction.");
                return false;
            }
        }

        /* ------------------------- Invalid tests ------------------------ */
        // Testing invalid addFunds()
        try {

            CustomerAccount customer = new CustomerAccount("Bob", "Bobb", 10000,
                    new Car("5555e", "Toyota", 5));

            customer.addFunds(-100);

            System.out.println("Invalid case: FAILED: "
                    + "Exception not thrown when negative funds were added.");
            return false;

        } catch (IllegalArgumentException exception) {
            // Intended to be thrown
        }

        // Testing makeTrip()
        try {

            CustomerAccount customer = new CustomerAccount("Bob", "Bobb", 0,
                    new Van("g25e", "Mercedes", 1000));

            customer.makeTrip();

            System.out.println(
                    "Invalid case: FAILED: "
                            + "Exception not thrown when trip was made without sufficient funds.");
            return false;

        } catch (InsufficientAccountBalanceException exception) {
            // Intended to be thrown
        }

        System.out.println("CustomerAccount test: PASSED");
        return true;
    }

}
