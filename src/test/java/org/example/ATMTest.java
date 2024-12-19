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

    private ATM atm;
    private Account account;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        account = new Account(1020, 1234, true,0,129.40,true);
        atm = new ATM(bank,account);
    }





    @Test
    void getBalanceTestNotVerified() throws CustomExceptions {
        int card = account.getCardNumber();
        //mock metod för att se till att verified nekas
        when(bank.isVerified(card)).thenReturn(false);
        //prövar metodden
       double balance = atm.getBalance(card);

       // ser till att balance är 0.0 vid icke inloggad användare
       assertEquals(0.0,balance);
       //ser till att den körde isVerified men inte getBalance från mock banken
       verify(bank).isVerified(card);
       verify(bank, never()).getBalance(card);
    }

    @Test
    void getBalanceTestSuccessful() throws CustomExceptions {
        int card = account.getCardNumber();
        double expectedBalance = account.getBalance();

        when(bank.isVerified(card)).thenReturn(true);
        when(bank.getBalance(card)).thenReturn(expectedBalance);

        double actualBalance = atm.getBalance(card);

        assertEquals(expectedBalance, actualBalance);
        verify(bank).getBalance(card);
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
        assertThrows(CustomExceptions.class, () -> atm.handleInvalidCardRanges(-1));
        assertThrows(CustomExceptions.class, () -> atm.handleInvalidCardRanges(100000));
    }

    @DisplayName("Kollar så invalid error kan throw när den inte ska")
    @Test
    void handleInvalidRangesTestNoThrow(){
        assertDoesNotThrow(() -> atm.handleInvalidCardRanges(1234));
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