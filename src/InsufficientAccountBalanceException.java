/**
 * Thrown when the account has insufficient balance to complete the transaction.
 */
public class InsufficientAccountBalanceException extends Exception {

    public InsufficientAccountBalanceException(String message) {
        super(message);
    }
    
}
