package org.example;


/**
 * Bank (interface för att mocka en bank)
 */
public interface Bank {

    Account getAccountByCard(int cardNumber);

    boolean verifyAccount(int cardNumber, int pin);
    double getBalance(int card);
    boolean withdraw(int card,double amount);
    boolean isVerified(int card);
    boolean deposit(int card, double amount);
    boolean isBlocked(int card);
    int getFailedAttempts(int card);
    void increaseAttempts(int card);
    boolean blockCard(int card);
}
