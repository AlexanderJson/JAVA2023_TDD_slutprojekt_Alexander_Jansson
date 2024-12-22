package org.example;

/**
 * Egen exceptionklass för att hantera vissa felhanteringar som ogiltliga intervall etc.
 */
public class CustomExceptions extends Exception{


    public enum ErrorType{
        USER_BLOCKED("Detta kort är blockerat!"),
        NOT_AUTHORISED("Inget kort i maskinen!"),
        INVALID_RANGE_PIN_CARD("PIN och Kortnummer måste vara 4 siffror. Inga negativa tal tillåtna"),
        INVALID_RANGE_TRANSACTIONS_HUGE("Denna summa är för hög. Kontakta banken för ett sådant uttag."),
        INVALID_RANGE_TRANSACTIONS_NEGATIVE("Inga summor under 1 möjliga."),
        INVALID_TYPE_DOUBLE("Endast nummer tillåtet.");

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


