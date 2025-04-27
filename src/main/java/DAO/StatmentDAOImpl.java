package DAO;

import model.Currencies;
import model.ExchangeRates;

import java.sql.Connection;
import java.sql.ResultSet;

public interface StatmentDAOImpl {
    ResultSet selectAllCurrencies();
    void insertNewCurrency(Currencies currency);
    ResultSet getCurrency(Currencies currency);
    ResultSet selectAllExchangeRates();
    ResultSet selectCurrencyByID(Integer ID);
    ResultSet selectRateById(Integer baseCurrencyId, Integer targetCurrencyId);
    void insertNewExchangeRates(ExchangeRates exchangeRate);
    void updateRateByID(Integer rateID, Double rate);
}
