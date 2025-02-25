package ma.ehtp.ebank_backend.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message){
        super(message);
    }

}
