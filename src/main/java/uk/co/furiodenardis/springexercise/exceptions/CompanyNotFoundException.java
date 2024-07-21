package uk.co.furiodenardis.springexercise.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(final String message) { super(message);}
}
