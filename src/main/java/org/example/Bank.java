package org.example;

public interface Bank {

    Account getAccountByCard(int cardNumber);

    boolean verifyAccount(int cardNumber, int pin);
    boolean isBlocked(int card);

    int getFailedAttempts(int card);
    void increaseAttempts(int card);
    boolean lockCard(int card);

    double getBalance(int card);
    double withdraw(int card);
    double deposit(int card);
    boolean isVerified(int card);

}
