package edu.curtin.app.models;

/*
 * Exceptions for if the message format provided is incorrect.
 */

public class MessageFormatException extends Exception {
    public MessageFormatException(String message) {
        super(message);
    }
}
