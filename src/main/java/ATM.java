import java.util.Scanner;

public class ATM implements iATM {

    private static final int REFILL_RESTRICTION = 1_000_000;
    private String id_ATM;
    private int cashInATM;
    private String cardNumber;
    private BankProcessingCenter bankProcessingCenter;
    private boolean exitFlag;

    public ATM() {
    }

    public ATM(String id_ATM) {
        this.id_ATM = id_ATM;
        this.bankProcessingCenter = new BankProcessingCenter();
        this.cashInATM = bankProcessingCenter.findATM(this.id_ATM).cashInATM;
    }

    private void openMenu() {
        System.out.println(String.join("\n", "Select the desired action:",
                "- enter 1 to display balance",
                "- enter 2 to deposit money into an account",
                "- enter 3 to withdraw cash",
                "- enter 0 to exit and get your card back"));
        switch (getIntValue()) {
            case 1:
                showBalance();
                break;
            case 2:
                deposit();
                break;
            case 3:
                giveCash();
                break;
            case 0:
                returnCard();
                break;
            default:
                System.out.println("You entered an incorrect value. Please try again");
        }
    }

    @Override
    public void giveCash() {
        System.out.println("Enter the cash removal amount");
        int cash = getIntValue();
        if (cash > this.cashInATM) {
            System.out.println("Not enough money in ATM");
            return;
        }
        if (bankProcessingCenter.withdraw(cash)) {
            System.out.println("Operation was successfully completed." + "\nPlease take money");
            this.cashInATM -= cash;
        } else {
            System.out.println("Access denied");
            checkAccessStatus();
        }
    }

    @Override
    public void deposit() {
        System.out.println("Enter the amount to deposit");
        int depositAmount = getIntValue();
        if (depositAmount > REFILL_RESTRICTION) {
            System.out.println("You entered a value exceeding the allowable to replenishment. " +
                    "Please specify a value less than " + REFILL_RESTRICTION);
            return;
        }
        if (!checkInputValue(depositAmount)) {
            System.out.println("You entered the value less than 1");
            return;
        }
        if (bankProcessingCenter.deposit(depositAmount)) {
            this.setCashInATM(this.getCashInATM() + depositAmount);
            System.out.println("Operation was successfully completed.");
        } else {
            System.out.println("Access denied");
            checkAccessStatus();
        }
    }

    @Override
    public String getStringValue() {
        return new Scanner(System.in).nextLine();
    }

    @Override
    public int getIntValue() {
        return new Scanner(System.in).nextInt();
    }

    @Override
    public void pickUpCard() {
        System.out.println("Welcome, Dear customer");
        while (!exitFlag) {
            if (authorize()) {
                while (!exitFlag) {
                    if (checkPIN()) {
                        openMenu();
                    }
                }
            } else {
                System.out.println("Account is not found");
            }
        }
    }

    @Override
    public boolean authorize() {
        System.out.println("Please enter your card number or 0 to exit");
        cardNumber = getStringValue();
        if (cardNumber.equals("0")) {
            returnCard();
            return false;
        }
        if (!cardNumber.matches(CardAccount.getNumberFormat())) {
            System.out.println("The card number you entered is not in the correct format. " +
                    "It should be: XXXX-XXXX-XXXX-XXXX");
            return false;
        }
        return (bankProcessingCenter.authorize(cardNumber));
    }

    @Override
    public boolean checkPIN() {
        System.out.println("Please enter your PIN to perform an operation or 0 to exit");
        String pinCode = getStringValue();
        if (pinCode.equals("0")) {
            returnCard();
            return false;
        }
        if (bankProcessingCenter.isBlocked()) {
            System.out.println("Card account is blocked. Please try later");
            return false;
        }
        boolean result = bankProcessingCenter.checkPIN(pinCode);
        if (!result) {
            System.out.printf("You entered invalid PIN. You have %d remaining attempt(s).\n",
                    bankProcessingCenter.getRemainingAttempts());
            return false;
        }
        return true;
    }

    @Override
    public void showBalance() {
        String balance = bankProcessingCenter.getBalance();
        if (balance != null) {
            System.out.println("Account balance is: " + balance);
        } else {
            System.out.println("Access denied");
            checkAccessStatus();
        }
    }

    @Override
    public void returnCard() {
        System.out.println("Thank you for using the services of our bank");
        bankProcessingCenter.findATM(this.id_ATM).setCashInATM(this.cashInATM);
        bankProcessingCenter.endSession();
        exitFlag = true;
    }

    private void checkAccessStatus() {
        if (bankProcessingCenter.isBlocked()) {
            System.out.println("Your account is blocked. Please try later");
        }
    }

    private boolean checkInputValue(int inputValue) {
        if (inputValue < 1) {
            return false;
        }
        return true;
    }

    public String getId_ATM() {
        return id_ATM;
    }

    public int getCashInATM() {
        return cashInATM;
    }

    public void setCashInATM(int cashInATM) {
        this.cashInATM = cashInATM;
    }

    public void setId_ATM(String id_ATM) {
        this.id_ATM = id_ATM;
    }

    public ATM fromStringArray(String[] records) {
        ATM atm = new ATM();
        try {
            atm.setId_ATM(records[0]);
            atm.setCashInATM(Integer.parseInt(records[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atm;
    }

    public String[] toStringArray() {
        String[] atmStringArray = new String[2];
        atmStringArray[0] = getId_ATM();
        atmStringArray[1] = String.valueOf(getCashInATM());
        return atmStringArray;
    }
}
