package BostonGeneTest.controller;

/**
 * Created by Orin on 12.04.2018.
 */
public class EmailAlreadyExistsException extends Exception {
    EmailAlreadyExistsException(String message) {
        super(message);
    }
}
