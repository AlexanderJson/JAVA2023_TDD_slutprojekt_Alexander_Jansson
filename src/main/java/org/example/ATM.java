package org.example;
import org.example.CustomExceptions;
/**
 * ATM klassen, innehåller all logik
 */
public class ATM {

    private final Bank bank;
    private Account account;

    /**
     *  Skapar en instans av {@code ATM}
     *
     * @param bank Banken som används - endast för mocking
     * @param account kontot som ska vara aktivt på ATM vid lyckas inloggning
     */

    public ATM(Bank bank, Account account) {
        this.bank = bank;
        this.account = account;
    }


    /**
     * @return {@code account} som är inloggat
     */

    public Account getAccount() {
        return account;
    }


    /**
     *
     * @param card kortnumret
     * @param pin pin koden
     * @return {@code true} of verifiering lyckas, annars {@code false}
     * @throws CustomExceptions kastas om kortnumret är felaktigt intervall
     */

    public boolean verifyAccount(int card, int pin) throws CustomExceptions {
                handleInvalidCardRanges(card);
                if (bank.verifyAccount(card, pin)) {
                    account = bank.getAccountByCard(card);
                    handleLoginAttempt(card);
                    return true;
                }
        return false;
    }


    /**
     * Hanterar misslyckade inloggningsförsök.
     * Om användaren gör tre i rad, blockeras kortet.
     *
     * @param card Kortnummer
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

    /**
     * Blockerar kortet. Används i samband med
     *  {@link ATM#verifyAccount(int, int)}
     */

    public void blockCard(int card){
        bank.blockCard(card);
    }


    /**
     * Ökar antal inloggningsförsök (max 3). Används i samband med
     *  {@link ATM#handleLoginAttempt(int)}
     */
    public void increaseLoginAttempts(int card){
        bank.increaseAttempts(card);
    }

    /**
     *  Hämtar antal inloggninsförsök. Används i samband med
     *  {@link ATM#handleLoginAttempt(int)}
     */
    public int getLoginAttempts(int card){
        return bank.getFailedAttempts(card);
    }

    /**
     * Avslutar session. Sätter konto som null, vilket gör verifiering till {@code false}
     * Används i samband med {@link ATM#verifyAccount(int, int)}
     */
    public void exit(){
        System.out.println("Exiting..");
        account = null;
    }



    /**
     * Metod för att sätta in pengar
     *  @param card   Kortnummer.
     *  @param amount Belopp att sätta in.
     *  @return {@code true} om transaktionen lyckas, annars {@code false}.
     */
    public boolean deposit(int card, double amount){
        if (isVerified(card)){
            System.out.println("Transaktion Framgångsrik. " + amount + " SEK insatt på " + card);
            return bank.deposit(card,amount);
        }
        return false;
    }


    /**
     * Metod för att ta ut pengar
     * @param card   Kortnummer.
     * @param amount Belopp att ta ut.
     * @return {@code true} om transaktionen lyckas, annars {@code false}.
     */
    public boolean withdraw(int card, double amount){
            if (isVerified(card) && hasMoney(card,amount)){
                System.out.println("Transaktion Framgångsrik. " + amount + " SEK uttaget från " + card);
                return bank.withdraw(card,amount);
            }
        System.out.println("Uttaget misslyckades");
        return false;
    }


    /**
     * Metod för att kolla om användaren har tillräckligt med pengar.
     * Använder {@link Bank#getBalance(int)}
     * @param card   Kortnummer.
     * @param amount Belopp att kontrollera.
     * @return {@code true} om det finns tillräckligt med pengar, annars {@code false}.
     */
    public boolean hasMoney(int card, double amount){
        double balance = bank.getBalance(card);
        return !(balance < amount);
    }

    /**
     * Används för att hämta användarens saldo på konto.
     * @param card Kortnummer.
     * @return {@code double balance} AKA pengar på kontot
     * @throws CustomExceptions Om användaren inte är verifierad.
     */
    public double getBalance(int card) throws CustomExceptions {
        if (!isVerified(card)){
            throw new CustomExceptions(CustomExceptions.ErrorType.NOT_AUTHORISED);
        }
        double balance = bank.getBalance(card);
        System.out.println("You have " + balance);
        return balance;
    }


    /**
     * Kollar så användaren är verifierad. Aktiveras med
     * {@link ATM#verifyAccount(int, int)}
     * @param card kortnummret
     * @return {@code true} eller {@code false}
     */
    public boolean isVerified(int card) {
        if (account == null){
            System.out.println("Insert your card");
            return false;
        }
        return true;
    }


    /**
     * Helper metod, kollar så kortnummer/pin kod inte är negativa eller för höga. Måste vara
     * 4 siffror.
     * @param n antingen kortnummer eller pinkod
     * @throws CustomExceptions Om kortnumret är ogiltigt.
     */
    public void handleInvalidCardRanges(int n) throws CustomExceptions {
        int minDigits = 1000;
        int maxDigits = 9999;
        if (n <= minDigits || n >=maxDigits) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_PIN_CARD);
        }
    }

    /**
     * Kollar så att transaktionsbeloppet är inom giltigt intervall.
     * Sätter en ribba på för höga belopp med av två anledningar:
     * 1. Overflow kommer ske om vi går över {@code Double.MAX_VALUE}
     * detta hade kunnats unyttjats för att belasta appen.
     * 2. Det hade varit orimligt att låta en användare ta ut mer än 1 miljon
     * utan någon extra bekräftelse. Tror jag!
     * @param n Belopp att kontrollera.
     * @throws CustomExceptions Om beloppet är negativt eller för stort.
     */
    public void handleInvalidDoubleRanges(double n) throws CustomExceptions {
        double maxValue = 1000000.00;
        //Double.MAX_VALUE;
        if (n <= 0) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_TRANSACTIONS_NEGATIVE);
        }
        if (n >= maxValue) {
            throw new CustomExceptions(CustomExceptions.ErrorType.INVALID_RANGE_TRANSACTIONS_HUGE );
        }
    }

    }

