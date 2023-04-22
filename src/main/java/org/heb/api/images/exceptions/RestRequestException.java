package org.heb.api.images.exceptions;

public class RestRequestException extends Exception {

    public RestRequestException(String message, Throwable e) {
        super(message, e);
    }
}
