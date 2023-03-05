/**
 * Customer account class to represent individual customers.
 */
public class CustomerAccount implements Comparable<CustomerAccount> {

    private enum Discounts {
        STAFF,
        FRIENDS_AND_FAMILY,
        NONE
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
    public CustomerAccount(String firstName, String lastName,
            int balance, Vehicle vehicle) {

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
     * @throws InsufficientAccountBalanceException
     */
    public void makeTrip() throws InsufficientAccountBalanceException {

        int toll = this.vehicle.calculateBasicTripCost();

        if (this.discount == Discounts.STAFF) {
            toll = (int) Math.floor(toll * 0.5d);

        } else if (this.discount == Discounts.FRIENDS_AND_FAMILY) {
            toll = (int) Math.floor(toll * 0.9d);
        }

        if (toll > this.balance) {
            throw new InsufficientAccountBalanceException(
                    "Customer lacks the sufficient funds to complete transaction.");
        }

        this.balance -= toll;
    }

    @Override
    /**
     * Compare registration alphabetical order.
     * 
     * @param customerAccount The account to compare to
     * @return Comparison integer
     */
    public int compareTo(CustomerAccount customerAccount) {

        return this.getVehicle().getRegistration().compareTo(
                customerAccount.getVehicle().getRegistration());
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

}
