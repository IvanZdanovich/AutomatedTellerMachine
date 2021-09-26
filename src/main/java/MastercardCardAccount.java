public class MastercardCardAccount extends CardAccount {

    private static final String NUMBER_FORMAT = "[2]\\d{3}-\\d{4}-\\d{4}-\\d{4}";
    private String number;
    private String pin;
    private int balance;
    private boolean block;
    private boolean access;
    private int remainingAttempts;

    public MastercardCardAccount() {
        super();
    }

    @Override
    public String getNumber() {
        return super.getNumber();
    }

    @Override
    public void setNumber(String number) {
        super.setNumber(number);
    }

    @Override
    public String getPin() {
        return super.getPin();
    }

    @Override
    public void setPin(String pin) {
        super.setPin(pin);
    }

    @Override
    public int getBalance() {
        return super.getBalance();
    }

    @Override
    public void setBalance(int balance) {
        super.setBalance(balance);
    }

    @Override
    public boolean isBlock() {
        return super.isBlock();
    }

    @Override
    public void setBlock(boolean block) {
        super.setBlock(block);
    }

    @Override
    public boolean isAccess() {
        return super.isAccess();
    }

    @Override
    public void setAccess(boolean access) {
        super.setAccess(access);
    }

    @Override
    public int getRemainingAttempts() {
        return super.getRemainingAttempts();
    }

    @Override
    public void setRemainingAttempts(int remainingAttempts) {
        super.setRemainingAttempts(remainingAttempts);
    }

    public static String getNumberFormat() {
        return NUMBER_FORMAT;
    }

    @Override
    public MastercardCardAccount fromStringArray(String[] records) {
        MastercardCardAccount mastercardCardAccount = new MastercardCardAccount();
        try {
            mastercardCardAccount.setNumber(records[0]);
            mastercardCardAccount.setPin(records[1]);
            mastercardCardAccount.setBalance(Integer.parseInt(records[2]));
            mastercardCardAccount.setBlock(Boolean.parseBoolean(records[3]));
            mastercardCardAccount.setAccess(Boolean.parseBoolean(records[4]));
            mastercardCardAccount.setRemainingAttempts(Integer.parseInt(records[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mastercardCardAccount;
    }

    @Override
    public String[] toStringArray() {
        String[] mastercardCardAccount = new String[6];
        mastercardCardAccount[0] = getNumber();
        mastercardCardAccount[1] = getPin();
        mastercardCardAccount[2] = String.valueOf(getBalance());
        mastercardCardAccount[3] = String.valueOf(isBlock());
        mastercardCardAccount[4] = String.valueOf(isAccess());
        mastercardCardAccount[5] = String.valueOf(getRemainingAttempts());
        return mastercardCardAccount;
    }
}
