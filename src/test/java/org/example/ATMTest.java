package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        account = new Account("1020", "1234", true,0);
    }


    @Test
    void verifyAccountCorrectDetails() {
        String card = account.getCardNumber();
        String pin = account.getPin();
        when(bank.verifyAccount(card,pin)).thenReturn(true);
        boolean result = atm.verifyAccount(card,pin);
        assertTrue(result, "verifyAccount ska returnera ett true value");
        verify(bank).verifyAccount(card,pin);
    }

    @Test
    void verifyAccountIncorrectDetails() {
        String card = account.getCardNumber();
        String pin = account.getPin();
        when(bank.verifyAccount(card,pin)).thenReturn(false);
        boolean result = atm.verifyAccount(card,pin);
        assertFalse(result, "verifyAccount ska returnera ett false value");
        verify(bank).verifyAccount(card,pin);
    }


    @Test
    void loginCard() {

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