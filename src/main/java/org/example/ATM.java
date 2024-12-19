package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ATM {

    private final Bank bank;
    private Account account = null;



    public ATM(Bank bank, Account account){
        this.bank = bank;
        this.account = account;
    }


    public void increaseLoginAttempts(int card){

    }

    public int getLoginAttempts(int card){
        return 0;
    }



    public boolean deposit(int card, double amount){
        if (isVerified(card)){
            return bank.deposit(card,amount);
        }
        return false;
    }

    public boolean withdraw(int card, double amount){
        if (isVerified(card)){
            return bank.withdraw(card,amount);
        }return false;
    }

    public boolean isVerified(int card){
        return bank.isVerified(card);
    }

    public double getBalance(int card) throws CustomExceptions {
        handleInvalidCardRanges(card);
        if (isVerified(card)){
            double balance = bank.getBalance(card);
            System.out.println("You have: " + balance);
            return balance;
        }
        System.out.println("No account found");
        return 0.0;
    }

    public boolean verifyAccount(int card, int pin) throws CustomExceptions {
        handleInvalidCardRanges(card);
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

    public void handleInvalidCardRanges(int n) throws CustomExceptions {
        //todo - bättre sätt
        if (n <= 1000 || n >=9999) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_PIN_CARD);
        }
    }

    public void handleInvalidTransactionalRanges(int n) throws CustomExceptions {
        if (n < 0 || n >=999999999) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_PIN_CARD);
        }
    }


}