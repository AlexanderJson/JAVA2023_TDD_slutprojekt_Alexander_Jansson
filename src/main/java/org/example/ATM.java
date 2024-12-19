package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ATM {

    private final Bank bank;



    private Account account;
    public Account getAccount() {
        return account;
    }



    public ATM(Bank bank, Account account){
        this.bank = bank;
        this.account = account;
    }


    /**
     * Verifieringsmetoder. Hanterar inloggning,logout och verifierings under runtime.
     */
    public boolean verifyAccount(int card, int pin) throws CustomExceptions {
        handleInvalidCardRanges(card);
        account = bank.getAccountByCard(card);
        if (bank.verifyAccount(card,pin)){
            System.out.println("Framgång!");
            return true;
        }
        handleLoginAttempt(card);
        System.out.println("Fail!");
        return false;
    }

    public boolean isVerified(int card){
            return bank.isVerified(card);
    }

    public void exit(){
        System.out.println("Exiting..");
        account = null;
    }


    /**
     * Blocking metoder. Tre metoder som hämtar "data". En metod hanterar
     * affärslogik.
     */

    public void handleLoginAttempt(int card){
        increaseLoginAttempts(card);
        int attemptsLeft = getLoginAttempts(card);
        if (attemptsLeft == 3){
            System.out.println("Account is blocked");
            blockCard(card);
        } else {
            System.out.println("You have " + (3 - attemptsLeft) + " left until blocked.");
        }
    }

    public void blockCard(int card){
        bank.blockCard(card);
    }

    public void increaseLoginAttempts(int card){
        bank.increaseAttempts(card);
    }

    public int getLoginAttempts(int card){
        return bank.getFailedAttempts(card);
    }


    /**
     * Transaction metoder
     */
    public boolean deposit(int card, double amount){
        if (isVerified(card)){
            System.out.println("Transaktion Framgångsrik. " + amount + " SEK insatt på " + card);
            return bank.deposit(card,amount);
        }
        return false;
    }

    public boolean withdraw(int card, double amount){
            if (isVerified(card) && hasMoney(card,amount)){
                System.out.println("Transaktion Framgångsrik. " + amount + " SEK uttaget från " + card);
                return bank.withdraw(card,amount);
            }
        System.out.println("Uttaget misslyckades");
        return false;
    }

    public boolean hasMoney(int card, double amount){
        double balance = bank.getBalance(card);
        return !(balance < amount);
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

    public void handleInvalidCardRanges(int n) throws CustomExceptions {
        double minDigits = 1000;
        double maxDigits = 9999;
        if (n <= minDigits || n >=maxDigits) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_PIN_CARD);
        }
    }

    public void handleInvalidDoubleRanges(double n) throws CustomExceptions {
        double maxValue = 100000.00;
        if (n <= 0) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_TRANSACTIONS_NEGATIVE);
        }
        if (n >= maxValue) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_TRANSACTIONS_HUGE );
        }
    }


}