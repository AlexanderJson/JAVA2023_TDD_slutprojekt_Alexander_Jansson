package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Account {
    private int cardNumber;

    public Account(int cardNumber, int pin, boolean isBlocked, int loginAttempts) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isBlocked = isBlocked;
        this.loginAttempts = loginAttempts;
    }

    private int pin;
    private boolean isBlocked;
    private int loginAttempts;

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



}