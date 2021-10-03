public final class ATMSiemens extends AbstractATM {

    public ATMSiemens() {
    }

    public ATMSiemens(String id_ATM) {
        this.id_ATM = id_ATM;
        this.bankProcessingCenter = new BankProcessingCenter();
        this.cashInATM = bankProcessingCenter.findATM(this.id_ATM).getCashInATM();
        REFILL_RESTRICTION = 1_000_000;
    }

    public ATMSiemens fromStringArray(String[] records) {
        ATMSiemens atmSiemens = new ATMSiemens();
        try {
            atmSiemens.setId_ATM(records[0]);
            atmSiemens.setCashInATM(Integer.parseInt(records[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atmSiemens;
    }

    public String[] toStringArray() {
        String[] atmStringArray = new String[2];
        atmStringArray[0] = getId_ATM();
        atmStringArray[1] = String.valueOf(this.getCashInATM());
        return atmStringArray;
    }
}
