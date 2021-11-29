package iu.cse.lannis.servicecourse.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceCourseException extends RuntimeException{

    private final HttpStatus status;

    public ServiceCourseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
