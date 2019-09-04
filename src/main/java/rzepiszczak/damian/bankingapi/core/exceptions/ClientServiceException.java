package rzepiszczak.damian.bankingapi.core.exceptions;

public class ClientServiceException extends RuntimeException {
    public ClientServiceException(String message) {
        super(message);
    }
}
