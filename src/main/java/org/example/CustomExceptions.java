package org.example;

/**
 * @author Alexander Jansson
 * @version 1.0
 */
public class CustomExceptions extends Exception{

    /**
     * Dynamic way to handle my custom exceptions
     */
    public enum ErrorType{
        USER_BLOCKED("You are currently blocked"),
        NOT_AUTHORISED("No card in the machine, returning to atm menu!"),
        INVALID_RANGE_PIN_CARD("Input needs to be 4 numbers, no negative numbers allowed");

        private final String message;

        ErrorType(String message) {
            this.message = message;
        }

        public String getMessage(){
            return message;
        }

    }
    public CustomExceptions(ErrorType error){
        super(error.getMessage());
    }
}


