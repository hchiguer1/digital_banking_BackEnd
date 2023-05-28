package ma.enset.ebankbackend.exception;

public class BalanceNotsufficientException extends Exception{
    public BalanceNotsufficientException(String message){
        super(message) ;
    }
}
