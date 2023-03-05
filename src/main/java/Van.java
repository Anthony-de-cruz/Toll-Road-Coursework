package main.java;

public class Van extends Vehicle {

    private int payload;

    /**
     * @param registration The van's registration number
     * @param manufacturer The van's manufacturer
     * @param payload The van's cargo capacity in Kg
     */
    public Van(String registration, String manufacturer, int payload) {

        super(registration, manufacturer);
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

    public String toString() {
        
        return "Vehicle type: Van" +
        ", Registration: " + this.getRegistration() + 
        ", Manufacturer: " + this.getManufacturer() + 
        ", Payload: " + this.getPayload();
    }
    
    public int getPayload() {
        return payload;
    }

}
