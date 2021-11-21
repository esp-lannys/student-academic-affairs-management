package iu.cse.lannis.serviceretention.exception;

import org.springframework.http.HttpStatus;

public class ServiceRetentionException extends RuntimeException {
    private final HttpStatus status;

    public ServiceRetentionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
