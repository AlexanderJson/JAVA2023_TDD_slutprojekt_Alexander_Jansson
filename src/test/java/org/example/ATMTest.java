package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ATMTest {

    @Mock
    private Bank bank;

    @InjectMocks
    private ATM atm;
    private Account account;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        atm = new ATM(bank);
        account = new Account(1020, 1234, true,0);
    }


    @DisplayName("Denna metod testar om exception kan bli thrown i verifyAccount metoden")
    @Test
    void verifyAccountInvalidRangeThrows() throws CustomExceptions {
        int invalidCardNegative = -1;
        int validCardOverThousand = 10000;
        int validPin = 1234;

        CustomExceptions exceptionsNegative = assertThrows(CustomExceptions.class,
                () -> {atm.verifyAccount(invalidCardNegative,validPin);
        });
        assertEquals(CustomExceptions.ErrorType.INVALID_RANGE_PIN_CARD.getMessage(),exceptionsNegative.getMessage());

        CustomExceptions exceptionsPositive = assertThrows(CustomExceptions.class,
                () -> {atm.verifyAccount(validCardOverThousand,validPin);
                });
        assertEquals(CustomExceptions.ErrorType.INVALID_RANGE_PIN_CARD.getMessage(),exceptionsPositive.getMessage());

    }

    @DisplayName("Testar så rätt kort eller pin skickar true response")
    @Test
    void verifyAccountCorrectDetails() throws CustomExceptions {
        int card = account.getCardNumber();
        int pin = account.getPin();
        when(bank.verifyAccount(card,pin)).thenReturn(true);
        boolean result = atm.verifyAccount(card,pin);
        assertTrue(result, "verifyAccount ska returnera ett true value");
        verify(bank).verifyAccount(card,pin);
    }

    @DisplayName("Testar så fel kort eller pin skickar false response")
    @Test
    void verifyAccountIncorrectDetails() throws CustomExceptions {
        int card = account.getCardNumber();
        int pin = account.getPin();
        when(bank.verifyAccount(card,pin)).thenReturn(false);
        boolean result = atm.verifyAccount(card,pin);
        assertFalse(result, "verifyAccount ska returnera ett false value");
        verify(bank).verifyAccount(card,pin);
    }

    @DisplayName("Kollar så invalid error kan throw när det ska")
    @Test
    void handleInvalidRangesTestThrow(){
        assertThrows(CustomExceptions.class, () -> atm.handleInvalidRanges(-1));
        assertThrows(CustomExceptions.class, () -> atm.handleInvalidRanges(100000));
    }

    @DisplayName("Kollar så invalid error kan throw när den inte ska")
    @Test
    void handleInvalidRangesTestNoThrow(){
        assertDoesNotThrow(() -> atm.handleInvalidRanges(1234));
    }

    @Test
    void blockCard() {

    }

    @Test
    void increaseLoginAttempts() {

    }

    @Test
    void getLoginAttempts() {

    }

    @Test
    void getBalance() {
    }

    @Test
    void deposit() {
    }

    @Test
    void withdraw() {

    }

    @Test
    void isVerified() {
    }

    @Test
    void exit() {
    }


}