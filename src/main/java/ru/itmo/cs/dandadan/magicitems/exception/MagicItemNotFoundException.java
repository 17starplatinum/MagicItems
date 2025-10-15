package ru.itmo.cs.dandadan.magicitems.exception;

public class MagicItemNotFoundException extends RuntimeException {
    public MagicItemNotFoundException(String message) {
        super(message);
    }
}
