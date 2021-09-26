public interface iATM {

    String getStringValue();

    int getIntValue();

    void pickUpCard();

    boolean authorize();

    void giveCash();

    void deposit();

    void showBalance();

    void returnCard();

    boolean checkPIN();
}
