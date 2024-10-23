package com.humancloud.alumni.alumniportal.Exception;

public class userNotFoundException extends RuntimeException {

    public userNotFoundException(String msg) {
        super(msg);
    }
}
