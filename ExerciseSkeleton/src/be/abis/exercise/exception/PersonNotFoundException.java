package be.abis.exercise.exception;

public class PersonNotFoundException extends Exception {
    private String message;
    public PersonNotFoundException(String message) {
        this.message = message;
    }
}
