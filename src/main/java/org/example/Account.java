package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Account {
    private String cardNumber;

    public Account(String cardNumber, String pin, boolean isBlocked, int loginAttempts) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isBlocked = isBlocked;
        this.loginAttempts = loginAttempts;
    }

    private String pin;
    private boolean isBlocked;
    private int loginAttempts;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
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