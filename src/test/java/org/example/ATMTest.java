package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        account = new Account(1020, 1234, false,0,129.40,true);
        atm = new ATM(bank,account);
    }

    @ParameterizedTest
    @CsvSource({
            "1, false", "2, false", "3, true",
    })
    @DisplayName("Testar antal login försök")
    void handleLoginAttempt(int attempts){
        int card = account.getCardNumber();
        // ett misslyckas försök
        when(bank.getFailedAttempts(card)).thenReturn(attempts);
        atm.handleLoginAttempt(card);
        if (attempts == 3){
            verify(bank).increaseAttempts(card);
            verify(bank).blockCard(card);
        } else{
            verify(bank).increaseAttempts(card);
            verify(bank,never()).blockCard(card);
        }
    }

    @Test
    @DisplayName("Testar att blockering funkar")
    void blockAccountTestSuccessfulBlocking(){
    int card = account.getCardNumber();
    when(bank.blockCard(card)).thenReturn(true);
    atm.blockCard(card);
    verify(bank).blockCard(card);
    }

    @Test
    @DisplayName("Testar att själva metoden för att öka login attempts funkar")
    void increaseLoginAttemptsTestIncreasing(){
        int card = account.getCardNumber();
        atm.increaseLoginAttempts(card);
        verify(bank,times(1)).increaseAttempts(card);
    }


    @Test
    @DisplayName("Testar att blockning funkar")
    void handleLoginAttempt_Blocked(){
        int card = account.getCardNumber();
        when(bank.getFailedAttempts(card)).thenReturn(3);
        atm.handleLoginAttempt(card);
        verify(bank).increaseAttempts(card);
        verify(bank).blockCard(card);
    }

    @ParameterizedTest
    @CsvSource({
            "1230, 100.0, true",
            "1230, 100.0, false",
    })
    @DisplayName("Testar att getBalance funkar vid både verifierad och inte verifierad")
    void getBalanceTest(int card, double inventory, boolean isVerified) throws CustomExceptions {
        if (!isVerified){
            atm = new ATM(bank,null);
            assertThrows(CustomExceptions.class, () -> atm.getBalance(card));
            verify(bank, never()).getBalance(card);
        } else {
            when(bank.getBalance(card)).thenReturn(inventory);
            double actual = atm.getBalance(card);
            assertEquals(inventory,actual);
            verify(bank).getBalance(card);
        }
    }
    @ParameterizedTest
    @CsvSource({
            "1234,100.0,true,100.1",
            "1234,100.0,false,10.1",
            "2234,100.0,true, 19.10",
            "2234,1000.0,true, 19000.10",
            "2234,10000000.0,true, 19000.10",
    })
    @DisplayName("Testar att ta ut pengar returnerar rätt baserat på: verifierad status och om användaren har pengar eller inte ")
    void withdrawTestSuccessAndNotSuccess(int card, double sum, boolean isVerified, double inventory){
        if (!isVerified){
            atm = new ATM(bank,null);
        }
        when(bank.getBalance(card)).thenReturn(inventory);
        when(bank.withdraw(card,sum)).thenReturn(true);
        boolean r = atm.withdraw(card,sum);
        if (isVerified && inventory >= sum){
            assertTrue(r, "Deposit bör lyckas och hela metod kallas.");
            verify(bank).withdraw(card,sum);
        }
        else if (isVerified && inventory < sum){
            assertFalse(r, "Deposit bör misslyckas och inte kunna kalla deposit.");
            verify(bank, never()).withdraw(card,sum);
        }
        else {
            assertFalse(r, "Deposit bör misslyckas och inte kunna kalla deposit.");
            verify(bank, never()).withdraw(card,sum);
        }
    }
    @ParameterizedTest
    @CsvSource({
            "1234,100.0,true",
            "1234,100.0,false",
    })
    @DisplayName("Testar ifall deposit funkar med verifiering")
    void depositTestVerified(int card, double sum, boolean isVerified){
        if (!isVerified){
            atm = new ATM(bank,null);
            assertThrows(CustomExceptions.class, () -> atm.getBalance(card));
        }
        when(bank.deposit(card,sum)).thenReturn(true);

        boolean r = atm.deposit(card,sum);
        if (isVerified){
            assertTrue(r, "Deposit bör lyckas och hela metod kallas.");
            verify(bank).deposit(card,sum);
        }
        else{
            assertFalse(r, "Deposit bör misslyckas och inte kunna kalla deposit.");
            verify(bank, never()).deposit(card,sum);
        }
    }


    @ParameterizedTest
    @CsvSource({
            "1234,9999,false",
            "1234,1111,true",
            "1234,1111,true",
    })
    @DisplayName("Testar ifall inloggning funkar med verifiering (rätt och fel inlogg)")
    void verifyAccountTest(int card, int pin, boolean expected) throws CustomExceptions {
        when(bank.verifyAccount(card,pin)).thenReturn(expected);
       boolean result = atm.verifyAccount(card,pin);
       assertEquals(expected, result, "Should return  " + expected);
       verify(bank).verifyAccount(card,pin);
    }



    @Test
    @DisplayName("testar så exit kommer göra account null")
    void exit() {
        atm.exit();
        assertNull(atm.getAccount());
    }



    /**
     * Testar helper metoderna
     */


    /**
     *  {@link ATM#handleInvalidCardRanges(int)}
     */
    @ParameterizedTest
    @CsvSource({
            "999, INVALID_RANGE_PIN_CARD",
            "10000, INVALID_RANGE_PIN_CARD",
            "-1, INVALID_RANGE_PIN_CARD",
    })
    @DisplayName("Testar så invalid ranges för kort/pin throws i olika testfall")
    void handleInvalidCardRangesTest(int card){
        assertThrows(CustomExceptions.class, () -> atm.handleInvalidCardRanges(card));
    }


    /**
     *  {@link ATM#handleInvalidDoubleRanges(double)}
     */
    @ParameterizedTest
    @CsvSource({
            "1000000.00, INVALID_RANGE_TRANSACTIONS_HUGE",
            "-1.0, INVALID_RANGE_TRANSACTIONS_NEGATIVE",
    })
    @DisplayName("Testar så invalid ranges för summor throws i olika testfall")
    void handleInvalidDoubleRangesTest(double n){
        assertThrows(CustomExceptions.class, () -> atm.handleInvalidDoubleRanges(n));
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

    @DisplayName("Kollar så invalid card error kan throw när den inte ska")
    @Test
    void handleInvalidRangesTestNoThrow(){
        assertDoesNotThrow(() -> atm.handleInvalidCardRanges(1234));
    }


    @DisplayName("Kollar så invalid error kan throw när den inte ska")
    @Test
    void handleInvalidTransactionTestNoThrow(){
        assertDoesNotThrow(() -> atm.handleInvalidDoubleRanges(1200.00));
    }


}