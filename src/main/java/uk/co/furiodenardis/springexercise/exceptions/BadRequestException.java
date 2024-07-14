package uk.co.furiodenardis.springexercise.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(final String message) { super(message);}
}
