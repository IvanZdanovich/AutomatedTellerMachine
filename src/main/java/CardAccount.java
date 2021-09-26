import java.math.BigDecimal;

public class CardAccount {
    private static final String NUMBER_FORMAT = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
    private String number;
    private String pin;
    private BigDecimal balance;
    private boolean block;
    private boolean access;
    private int remainingAttempts;

    public CardAccount() {
    }

    public CardAccount(String number, String pin, BigDecimal balance) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
        block = false;
        access = false;
        remainingAttempts = 3;
    }

    protected static String getNumberFormat() {
        return NUMBER_FORMAT;
    }

    protected String getNumber() {
        return number;
    }

    protected void setNumber(String number) {
        this.number = number;
    }

    protected String getPin() {
        return pin;
    }

    protected void setPin(String pin) {
        this.pin = pin;
    }

    protected BigDecimal getBalance() {
        return balance;
    }

    protected void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    protected boolean isBlock() {
        return block;
    }

    protected void setBlock(boolean block) {
        this.block = block;
    }

    protected boolean isAccess() {
        return access;
    }

    protected void setAccess(boolean access) {
        this.access = access;
    }

    protected int getRemainingAttempts() {
        return remainingAttempts;
    }

    protected void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    protected CardAccount fromStringArray(String[] records) {
        CardAccount cardAccount = new CardAccount();
        try {
            cardAccount.setNumber(records[0]);
            cardAccount.setPin(records[1]);
            cardAccount.setBalance(new BigDecimal(records[2]));
            cardAccount.setBlock(Boolean.parseBoolean(records[3]));
            cardAccount.setAccess(Boolean.parseBoolean(records[4]));
            cardAccount.setRemainingAttempts(Short.parseShort(records[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardAccount;
    }

    protected String[] toStringArray() {
        String[] cardAccount = new String[6];
        cardAccount[0] = getNumber();
        cardAccount[1] = getPin();
        cardAccount[2] = String.valueOf(getBalance());
        cardAccount[3] = String.valueOf(isBlock());
        cardAccount[4] = String.valueOf(isAccess());
        cardAccount[5] = String.valueOf(getRemainingAttempts());
        return cardAccount;
    }

}
