package iu.cse.lannis.servicestudent.exception;

import org.springframework.http.HttpStatus;

public class ServiceStudentException extends RuntimeException{

    private final HttpStatus status;

    public ServiceStudentException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
