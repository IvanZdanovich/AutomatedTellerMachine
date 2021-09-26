import java.math.BigDecimal;

public class VisaCardAccount extends CardAccount {

    private static final String NUMBER_FORMAT = "[1]\\d{3}-\\d{4}-\\d{4}-\\d{4}";

    protected static String getNumberFormat() {
        return NUMBER_FORMAT;
    }

    @Override
    protected VisaCardAccount fromStringArray(String[] records) {
        VisaCardAccount visaCardAccount = new VisaCardAccount();
        try {
            visaCardAccount.setNumber(records[0]);
            visaCardAccount.setPin(records[1]);
            visaCardAccount.setBalance(new BigDecimal(records[2]));
            visaCardAccount.setBlock(Boolean.parseBoolean(records[3]));
            visaCardAccount.setAccess(Boolean.parseBoolean(records[4]));
            visaCardAccount.setRemainingAttempts(Integer.parseInt(records[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visaCardAccount;
    }

    @Override
    protected String[] toStringArray() {
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
