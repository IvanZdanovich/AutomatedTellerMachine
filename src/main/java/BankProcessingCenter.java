import java.util.ArrayList;
import java.util.List;

public class BankProcessingCenter {

    private List<VisaCardAccount> visaDatabase;
    private List<MastercardCardAccount> mastercardDatabase;
    private List<ATM> atmDatabase;
    private PaymentSystems cardAccountType;
    private CardAccount cardAccount;

    public BankProcessingCenter() {
        this.visaDatabase = DatabaseAPI.readVisaDatabase();
        this.mastercardDatabase = DatabaseAPI.readMastercardDatabase();
        this.atmDatabase = DatabaseAPI.readATMDatabase();
    }

    public boolean deposit(int value) {
        if (!this.cardAccount.isBlock()
                && this.cardAccount.isAccess()
                && checkInputValue(value)) {
            this.cardAccount.setBalance(this.cardAccount.getBalance() + value);
            return true;
        }
        this.cardAccount.setAccess(false);
        return false;
    }

    public boolean withdraw(int value) {
        if (!this.cardAccount.isBlock()
                && this.cardAccount.isAccess()
                && checkWithdrawValue(value)
                && checkInputValue(value)
                && cardAccount.getBalance() > 0) {
            this.cardAccount.setBalance(this.cardAccount.getBalance() - value);
            this.cardAccount.setAccess(false);
            return true;
        }
        return false;
    }

    public String getBalance() {
        if (!this.cardAccount.isBlock()
                && this.cardAccount.isAccess()) {
            this.cardAccount.setAccess(false);
            return String.valueOf(this.cardAccount.getBalance());
        }
        return null;
    }

    public static boolean checkNumberFormat(String number) {
        return number.matches(CardAccount.getNumberFormat());
    }

    private boolean checkInputValue(int value) {
        return value > 0 && value < Integer.MAX_VALUE;
    }

    private boolean checkWithdrawValue(int value) {
        return value <= this.cardAccount.getBalance();
    }

    public boolean checkPIN(String pin) {
        cardAccount.setAccess(false);
        if (cardAccount.isBlock()) {
            return false;
        }
        if (!this.cardAccount.getPin().equals(pin)) {
            this.cardAccount.setRemainingAttempts(this.cardAccount.getRemainingAttempts() - 1);
            if (this.cardAccount.getRemainingAttempts() < 1) {
                cardAccount.setBlock(true);
            }
            return false;
        } else {
            cardAccount.setAccess(true);
            cardAccount.setRemainingAttempts(3);
            return true;
        }
    }

    private boolean determineCardAccountType(String number) {
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

    public boolean isBlocked() {
        return cardAccount.isBlock();
    }

    public int getRemainingAttempts() {
        return cardAccount.getRemainingAttempts();
    }

    public boolean authorize(String cardNumber) {
        if (checkNumberFormat(cardNumber) && determineCardAccountType(cardNumber)) {
            if (cardAccountType.equals(PaymentSystems.VISA)) {
                findVisaCardAccount(cardNumber);
                return true;
            } else if (cardAccountType.equals(PaymentSystems.MASTERCARD)) {
                findMastercardCardAccount(cardNumber);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void findVisaCardAccount(String number) {
        for (VisaCardAccount visaCardAccount : this.visaDatabase) {
            if (number.equals(visaCardAccount.getNumber())) {
                this.cardAccount = visaCardAccount;
            }
        }
    }

    public void findMastercardCardAccount(String number) {
        for (MastercardCardAccount mastercardCardAccount : this.mastercardDatabase) {
            if (number.equals(mastercardCardAccount.getNumber())) {
                this.cardAccount = mastercardCardAccount;
            }
        }
    }

    public ATM findATM(String number) {
        for (ATM atm : this.atmDatabase) {
            if (number.equals(atm.getId_ATM())) {
                return atm;
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

    private List<String[]> convertATMDatabaseToStrings(List<ATM> database) {
        List<String[]> strings = new ArrayList<>();
        for (ATM atm : database) {
            strings.add(atm.toStringArray());
        }
        return strings;
    }

    public void endSession() {
        DatabaseAPI.writeMastercardDatabase(this.convertMastercardDatabaseToStrings(this.mastercardDatabase));
        DatabaseAPI.writeVisaDatabase(this.convertVisaDatabaseToStrings(this.visaDatabase));
        DatabaseAPI.writeATMDatabase(this.convertATMDatabaseToStrings(this.atmDatabase));
    }
}
