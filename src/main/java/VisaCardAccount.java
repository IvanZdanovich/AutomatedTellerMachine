public class VisaCardAccount extends CardAccount {

    private static final String NUMBER_FORMAT = "[1]\\d{3}-\\d{4}-\\d{4}-\\d{4}";
    private String number;
    private String pin;
    private int balance;
    private boolean block;
    private boolean access;
    private int remainingAttempts;

    public VisaCardAccount() {
        super();
    }

    @Override
    public String getPin() {
        return super.getPin();
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
    public VisaCardAccount fromStringArray(String[] records) {
        VisaCardAccount visaCardAccount = new VisaCardAccount();
        try {
            visaCardAccount.setNumber(records[0]);
            visaCardAccount.setPin(records[1]);
            visaCardAccount.setBalance(Integer.parseInt(records[2]));
            visaCardAccount.setBlock(Boolean.parseBoolean(records[3]));
            visaCardAccount.setAccess(Boolean.parseBoolean(records[4]));
            visaCardAccount.setRemainingAttempts(Integer.parseInt(records[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visaCardAccount;
    }

    @Override
    public String[] toStringArray() {
        String[] visaCardAccount = new String[6];
        visaCardAccount[0] = getNumber();
        visaCardAccount[1] = getPin();
        visaCardAccount[2] = String.valueOf(getBalance());
        visaCardAccount[3] = String.valueOf(isBlock());
        visaCardAccount[4] = String.valueOf(isAccess());
        visaCardAccount[5] = String.valueOf(getRemainingAttempts());
        return visaCardAccount;
    }
}
