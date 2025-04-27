package model;

public class ExchangeRates {
    private Integer ID;
    private Integer BaseCurrencyID;
    private Integer TargetCurrencyID;
    private Double Rate;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getBaseCurrencyID() {
        return BaseCurrencyID;
    }

    public void setBaseCurrencyID(Integer baseCurrencyID) {
        BaseCurrencyID = baseCurrencyID;
    }

    public Integer getTargetCurrencyID() {
        return TargetCurrencyID;
    }

    public void setTargetCurrencyID(Integer targetCurrencyID) {
        TargetCurrencyID = targetCurrencyID;
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double Rate) {
        this.Rate = Rate;
    }

    public String toString(Currencies baseCurrency, Currencies targetCurrency) {
        return "ExchangeRates{" +
                "ID=" + ID +
                ", BaseCurrency=" + baseCurrency.toString() +
                ", TargetCurrency=" + targetCurrency.toString() +
                ", Rate=" + Rate +
                '}';
    }

}
