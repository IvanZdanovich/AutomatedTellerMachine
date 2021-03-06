import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankProcessingCenter {

    private List<VisaCardAccount> visaDatabase;
    private List<MastercardCardAccount> mastercardDatabase;
    private List<ATMSiemens> atmSiemensDatabase;
    private PaymentSystems cardAccountType;
    private CardAccount cardAccount;

    BankProcessingCenter() {
        //load databases with accounts
        this.visaDatabase = DatabaseAPI.readVisaDatabase();
        this.mastercardDatabase = DatabaseAPI.readMastercardDatabase();
        //load database with ATM
        this.atmSiemensDatabase = DatabaseAPI.readATMDatabase();
    }

    boolean deposit(int value) {
        /* before operation check that card account isn't blocked,
       PIN is correct, input value is bigger than zero*/
        if (!this.cardAccount.isBlock()
                && this.cardAccount.isAccess()
                && checkInputValue(value)) {
            this.cardAccount.setBalance(this.cardAccount.getBalance().add(new BigDecimal(value)));
            //after operation close the access
            this.cardAccount.setAccess(false);
            return true;
        }
        return false;
    }

    boolean withdraw(int value) {
        /* before operation check that card account isn't blocked,
       PIN is correct, input value is bigger than zero,
       value less than balance of account*/
        if (!this.cardAccount.isBlock()
                && this.cardAccount.isAccess()
                && checkWithdrawValue(value)
                && checkInputValue(value)
                && cardAccount.getBalance().compareTo(BigDecimal.valueOf(0)) > 0) {
            this.cardAccount.setBalance(this.cardAccount.getBalance().subtract(BigDecimal.valueOf(value)));
            //after operation close the access
            this.cardAccount.setAccess(false);
            return true;
        }
        return false;
    }

    String getBalance() {
         /* before operation check that card account isn't blocked,
       PIN is correct*/
        if (!this.cardAccount.isBlock()
                && this.cardAccount.isAccess()) {
            //after operation close the access
            this.cardAccount.setAccess(false);
            return String.valueOf(this.cardAccount.getBalance());
        }
        return null;
    }

    static boolean checkNumberFormat(String number) {
        return number.matches(CardAccount.getNumberFormat());
    }

    private boolean checkInputValue(int value) {
        return value > 0 && value < Integer.MAX_VALUE;
    }

    private boolean checkWithdrawValue(int value) {
        return BigDecimal.valueOf(value).compareTo(this.cardAccount.getBalance()) <= 0;
    }

    boolean checkPIN(String pin) {
        if (cardAccount == null) {
            return false;
        }
        cardAccount.setAccess(false);
        // abort checking if account is blocked
        if (cardAccount.isBlock()) {
            return false;
        }
        /* in case when entered PIN is incorrect decrement value of remaining attempts
        and block account if value is less than 1 */
        if (!this.cardAccount.getPin().equals(pin)) {
            this.cardAccount.setRemainingAttempts(this.cardAccount.getRemainingAttempts() - 1);
            if (this.cardAccount.getRemainingAttempts() < 1) {
                cardAccount.setBlock(true);
            }
            return false;
        } else {
            // if PIN is correct open the access
            cardAccount.setAccess(true);
            cardAccount.setRemainingAttempts(3);
            return true;
        }
    }

    boolean determineCardAccountType(String number) {
        if (number.matches(VisaCardAccount.getNumberFormat())) {
            cardAccountType = PaymentSystems.VISA;
            return true;
        }
        if (number.matches(MastercardCardAccount.getNumberFormat())) {
            cardAccountType = PaymentSystems.MASTERCARD;
            return true;
        }
        return false;
    }

    boolean isBlocked() {
        return cardAccount.isBlock();
    }

    int getRemainingAttempts() {
        return cardAccount.getRemainingAttempts();
    }

    boolean authorize(String cardNumber) {
        /*Check that the card number matches the format,
        determine the database to find the account*/
        if (checkNumberFormat(cardNumber) && determineCardAccountType(cardNumber)) {
            if (cardAccountType.equals(PaymentSystems.VISA)) {
                this.cardAccount = findVisaCardAccount(cardNumber);
                return this.cardAccount != null;
            } else if (cardAccountType.equals(PaymentSystems.MASTERCARD)) {
                this.cardAccount = findMastercardCardAccount(cardNumber);
                return this.cardAccount != null;
            }
        }
        return false;
    }

    VisaCardAccount findVisaCardAccount(String number) {
        for (VisaCardAccount visaCardAccount : this.visaDatabase) {
            if (number.equals(visaCardAccount.getNumber())) {
                return visaCardAccount;
            }
        }
        return null;
    }

    MastercardCardAccount findMastercardCardAccount(String number) {
        for (MastercardCardAccount mastercardCardAccount : this.mastercardDatabase) {
            if (number.equals(mastercardCardAccount.getNumber())) {
                return mastercardCardAccount;
            }
        }
        return null;
    }

    ATMSiemens findATM(String number) {
        for (ATMSiemens atmSiemens : this.atmSiemensDatabase) {
            if (number.equals(atmSiemens.getId_ATM())) {
                return atmSiemens;
            }
        }
        return null;
    }

    private List<String[]> convertVisaDatabaseToStrings(List<VisaCardAccount> database) {
        List<String[]> strings = new ArrayList<>();
        for (VisaCardAccount visaCardAccount : database) {
            strings.add(visaCardAccount.toStringArray());
        }
        return strings;
    }

    private List<String[]> convertMastercardDatabaseToStrings(List<MastercardCardAccount> database) {
        List<String[]> strings = new ArrayList<>();
        for (MastercardCardAccount mastercardCardAccount : database) {
            strings.add(mastercardCardAccount.toStringArray());
        }
        return strings;
    }

    private List<String[]> convertATMDatabaseToStrings(List<ATMSiemens> database) {
        List<String[]> strings = new ArrayList<>();
        for (ATMSiemens atmSiemens : database) {
            strings.add(atmSiemens.toStringArray());
        }
        return strings;
    }

    void endSession() {
        //upload data to databases
        DatabaseAPI.writeMastercardDatabase(this.convertMastercardDatabaseToStrings(this.mastercardDatabase));
        DatabaseAPI.writeVisaDatabase(this.convertVisaDatabaseToStrings(this.visaDatabase));
        DatabaseAPI.writeATMDatabase(this.convertATMDatabaseToStrings(this.atmSiemensDatabase));
    }
}
