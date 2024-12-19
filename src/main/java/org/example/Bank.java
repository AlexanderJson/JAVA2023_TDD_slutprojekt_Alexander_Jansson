package org.example;

public interface Bank {

    Account getAccountByCard(String cardNumber);

    boolean verifyAccount(String cardNumber, String pin);
    boolean isBlocked(String card);

    int getFailedAttempts(String card);
    void increaseAttempts(String card);
    boolean lockCard(String card);

    double getBalance(String card);
    double withdraw(String card);
    double deposit(String card);
    boolean isVerified(String card);

}
