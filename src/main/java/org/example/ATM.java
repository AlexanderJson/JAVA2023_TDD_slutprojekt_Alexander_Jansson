package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ATM {

    private final Bank bank;
    private Account account;



    public ATM(Bank bank){
        this.bank = bank;
    }

    public boolean loginCard(String card, String pin){
        return false;
    }

    public boolean blockCard(String card){
        return false;
    }

    public void increaseLoginAttempts(String card){

    }

    public int getLoginAttempts(String card){
        return 0;
    }

    public double getBalance(String card){
        return 0.0;
    }

    public boolean deposit(String card, double amount){
        return false;
    }

    public boolean withdraw(String card, double amount){
        return false;
    }

    public boolean isVerified(){
        return false;
    }

    public boolean verifyAccount(String card, String pin){
        account = bank.getAccountByCard(card);
        if (bank.verifyAccount(card,pin)){
            System.out.println("Framgång!");
            return true;
        }
        System.out.println("Fail!");
        return false;
    }

    public void exit(){
        account = null;
    }

}