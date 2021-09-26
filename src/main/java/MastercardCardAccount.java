import java.math.BigDecimal;

public class MastercardCardAccount extends CardAccount {

    private static final String NUMBER_FORMAT = "[2]\\d{3}-\\d{4}-\\d{4}-\\d{4}";

    protected static String getNumberFormat() {
        return NUMBER_FORMAT;
    }

    @Override
    protected MastercardCardAccount fromStringArray(String[] records) {
        MastercardCardAccount mastercardCardAccount = new MastercardCardAccount();
        try {
            mastercardCardAccount.setNumber(records[0]);
            mastercardCardAccount.setPin(records[1]);
            mastercardCardAccount.setBalance(new BigDecimal(records[2]));
            mastercardCardAccount.setBlock(Boolean.parseBoolean(records[3]));
            mastercardCardAccount.setAccess(Boolean.parseBoolean(records[4]));
            mastercardCardAccount.setRemainingAttempts(Integer.parseInt(records[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mastercardCardAccount;
    }

    @Override
    protected String[] toStringArray() {
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
