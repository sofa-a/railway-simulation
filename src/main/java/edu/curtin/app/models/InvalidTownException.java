package edu.curtin.app.models;

/*
 * Exceptions for issues that revolve around the town
 * e.g. town name incorrect, incorrect request
 */

public class InvalidTownException extends Exception {
    public InvalidTownException(String message) {
        super(message);
    }
}
