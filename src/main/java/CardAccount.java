public class CardAccount {
    private static final String NUMBER_FORMAT = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
    private String number;
    private String pin;
    private int balance;
    private boolean block;
    private boolean access;
    private int remainingAttempts;

    public CardAccount() {
    }

    public CardAccount(String number, String pin, int balance) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
        block = false;
        access = false;
        remainingAttempts = 3;
    }

    public static String getNumberFormat() {
        return NUMBER_FORMAT;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }


    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public CardAccount fromStringArray(String[] records) {
        CardAccount cardAccount = new CardAccount();
        try {
            cardAccount.setNumber(records[0]);
            cardAccount.setPin(records[1]);
            cardAccount.setBalance(Integer.parseInt(records[2]));
            cardAccount.setBlock(Boolean.parseBoolean(records[3]));
            cardAccount.setAccess(Boolean.parseBoolean(records[4]));
            cardAccount.setRemainingAttempts(Short.parseShort(records[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardAccount;
    }

    public String[] toStringArray() {
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
