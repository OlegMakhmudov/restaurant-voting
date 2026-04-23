package ru.javaops.bootjava.exception;

public class IllegalVoteException extends RuntimeException {
    public IllegalVoteException(String message) {
        super(message);
    }
}