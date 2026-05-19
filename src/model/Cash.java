package model;

public class Cash {
    private final String cashId;
    private final double currencyValue;
    private final String currencyName;

    public Cash(double currencyValue, String currencyName){
        this.cashId = currencyValue + currencyName;
        this.currencyValue = currencyValue;
        this.currencyName = currencyName;
    }

    public String getCashId() {
        return cashId;
    }

    public double getCurrencyValue() {
        return currencyValue;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
