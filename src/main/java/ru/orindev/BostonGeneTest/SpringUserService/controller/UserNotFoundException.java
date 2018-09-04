package ru.orindev.BostonGeneTest.SpringUserService.controller;

/**
 * Created by Orin on 12.04.2018.
 */
public class UserNotFoundException extends Exception {
    UserNotFoundException(String message) {
        super(message);
    }
}
