package tn.supcom.cot.exceptions;

public class NotAuthorizedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException() {
    }
}
