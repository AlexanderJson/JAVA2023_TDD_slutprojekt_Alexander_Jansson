package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Account {
    private int cardNumber;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private double balance;
    private int pin;
    private boolean isBlocked;
    private int loginAttempts;

    private boolean isVerified;

    public Account(boolean isVerified) {
        this.isVerified = isVerified;
    }


    public Account(int cardNumber, int pin, boolean isBlocked, int loginAttempts, double balance, boolean isVerified) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isBlocked = isBlocked;
        this.loginAttempts = loginAttempts;
    }



    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }



    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

}