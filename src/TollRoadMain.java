/**
 * @author Anthony de Cruz
 */
public class TollRoadMain {

    public static void main(String[] args) {

        runTests();
    }

    /**
     * Run all unit tests.
     */
    public static void runTests() {

        boolean passed = true;

        passed = passed && CustomerAccount.main();
        passed = passed && Car.main();
        passed = passed && Truck.main();
        passed = passed && Van.main();

        if (passed) {
            System.out.println("<-- All tests passed :) -->");

        } else {
            System.out.println("<-- Test failed -->");
        }
    }
    
}
