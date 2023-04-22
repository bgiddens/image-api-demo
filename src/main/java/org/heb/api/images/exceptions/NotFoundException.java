package org.heb.api.images.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super("Requested entity not found.");
    }
}
