package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ATM {

    private final Bank bank;
    private Account account;



    public ATM(Bank bank){
        this.bank = bank;
    }


    public void increaseLoginAttempts(int card){

    }

    public int getLoginAttempts(int card){
        return 0;
    }

    public double getBalance(int card){
        return 0.0;
    }

    public boolean deposit(int card, double amount){
        return false;
    }

    public boolean withdraw(int card, double amount){
        return false;
    }

    public boolean isVerified(){
        return false;
    }

    public boolean verifyAccount(int card, int pin) throws CustomExceptions {
        handleInvalidRanges(card);
        account = bank.getAccountByCard(card);
        if (bank.verifyAccount(card,pin)){
            System.out.println("Framgång!");
            return true;
        }
        System.out.println("Fail!");
        return false;
    }

    public boolean blockCard(int card){
        return false;
    }


    public void exit(){
        account = null;
    }

    public void handleInvalidRanges(int n) throws CustomExceptions {
        if (n <= 0 || n >=9999) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_PIN_CARD);
        }
    }

}