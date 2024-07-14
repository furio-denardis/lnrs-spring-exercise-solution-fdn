package uk.co.furiodenardis.springexercise.exceptions;

public class DownstreamServiceException extends RuntimeException {
    public DownstreamServiceException(final String message) { super(message);}
}
