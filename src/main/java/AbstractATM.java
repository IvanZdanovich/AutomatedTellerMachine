/* Basic class for all ATM's.*/

import java.util.Scanner;

abstract class AbstractATM {
    static int REFILL_RESTRICTION;
    String id_ATM;
    int cashInATM;
    String cardNumber;
    BankProcessingCenter bankProcessingCenter;
    boolean exitFlag;
    boolean escapeFlag;

    public AbstractATM() {
    }

    public void pickUpCard() {
        System.out.println("Welcome, Dear customer");
        while (!exitFlag) {
            if (authorize()) {
                escapeFlag = false;
                while (!escapeFlag) {
                    if (checkPIN()) {
                        openMenu();
                    }
                }
            }
        }
        System.out.println("Thank you for using the services of our bank");
    }

    protected boolean authorize() {
        System.out.println("Please enter your card number or 0 to exit");
        cardNumber = getStringValue();
        if (cardNumber.equals("0")) {
            return false;
        }
        if (!cardNumber.matches(CardAccount.getNumberFormat())) {
            System.out.println("The card number you entered is not in the correct format. " +
                    "It should be: XXXX-XXXX-XXXX-XXXX");
            return false;
        }
        return bankProcessingCenter.authorize(cardNumber);
    }

    protected boolean checkPIN() {
        System.out.println("Please enter your PIN to perform an operation or 0 to exit");
        String pinCode = null;
        while (pinCode == null) {
            pinCode = getStringValue();
        }
        if (pinCode.equals("0")) {
            returnCard();
            escapeFlag = true;
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

    protected void openMenu() {
        System.out.println(String.join("\n", "Select the desired action:",
                "- enter 1 to display balance",
                "- enter 2 to deposit money into an account",
                "- enter 3 to withdraw cash",
                "- enter 0 to escape"));
        int menuItem = getIntegerValue();
        switch (menuItem) {
            case 1:
                showBalance();
                break;
            case 2:
                deposit();
                break;
            case 3:
                withdraw();
                break;
            case 0:
                escapeFlag = true;
                break;
            default:
                System.out.println("You entered an incorrect value. Only 1, 2, 3 " +
                        "or 0 values are allowed. Please try again");
        }
    }

    protected void returnCard() {
        bankProcessingCenter.findATM(this.id_ATM).setCashInATM(this.cashInATM);
        bankProcessingCenter.endSession();
    }

    protected void withdraw() {
        System.out.println("Enter the cash removal amount");
        int cash = getIntegerValue();
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

    public void showBalance() {
        String balance = bankProcessingCenter.getBalance();
        if (balance != null) {
            System.out.println("Account balance is: " + balance);
        } else {
            System.out.println("Access denied");
            checkAccessStatus();
        }
    }

    protected void deposit() {
        System.out.println("Enter the amount to deposit");
        Integer depositAmount = getIntegerValue();
        if (depositAmount == null) {
            return;
        }
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

    public String getStringValue() {
        String value = null;
        try {
            value = new Scanner(System.in).nextLine();
            if (!value.matches("[\\d-]+")) {
                throw new Exception("Entered value is incorrect");
            }
        } catch (Exception e) {
            System.out.println("Please enter only digits or dash character");
        }
        return value;
    }

    public Integer getIntegerValue() {
        Integer value = null;
        try {
            value = new Scanner(System.in).nextInt();
            if (!checkInputValue(value)) {
                throw new Exception("Entered integer is less than 1");
            }
        } catch (Exception e) {
            System.out.println("Please enter an natural integer.");
        }
        return value;
    }

    protected void checkAccessStatus() {
        if (bankProcessingCenter.isBlocked()) {
            System.out.println("Your account is blocked. Please try later");
        }
    }

    protected boolean checkInputValue(Integer inputValue) {
        if (inputValue == null) {
            return false;
        }
        return inputValue > 0;
    }

    protected String getId_ATM() {
        return id_ATM;
    }

    Integer getCashInATM() {
        return cashInATM;
    }

    void setCashInATM(int cashInATM) {
        this.cashInATM = cashInATM;
    }

    void setId_ATM(String id_ATM) {
        this.id_ATM = id_ATM;
    }

    public static int getRefillRestriction() {
        return REFILL_RESTRICTION;
    }

    abstract AbstractATM fromStringArray(String[] records);

    abstract String[] toStringArray();
}
