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
    }

    /**
     * Run all unit tests.
     */
    public static void runTests() {

        boolean passed = true;

        passed = passed && Car.main();
        passed = passed && Truck.main();
        passed = passed && Van.main();
        passed = passed && CustomerAccount.main();
        passed = passed && TollRoad.main();

        if (passed) {
            System.out.println("<-- All tests passed :) -->");

        } else {
            System.out.println("<-- Test failed -->");
        }
    }

}
