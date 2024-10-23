package com.humancloud.alumni.alumniportal.Exception;

public class EmailAlreadyPresentException extends RuntimeException {
    public EmailAlreadyPresentException(String msg) {
        super(msg);
    }
}
