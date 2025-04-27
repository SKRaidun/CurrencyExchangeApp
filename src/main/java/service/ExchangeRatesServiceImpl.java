package service;

import model.Currencies;
import model.ExchangeRates;
import model.ExchangeResult;

import java.sql.SQLException;
import java.util.List;

public interface ExchangeRatesServiceImpl {
    List<ExchangeRates> getAllRates() throws SQLException;
    void insertNewExchangeRate(Currencies baseCurrency, Currencies targetCurrency, Double rate) throws SQLException;
    ExchangeRates getRate(ExchangeRates rate) throws SQLException;
    Currencies selectCurrencyByID(Integer ID) throws SQLException;
    ExchangeRates getRateByCode(String baseCurrencyCode, String targetCurrencyCode) throws SQLException;
    void updateRateByID(Integer rateId, Double rate);
    ExchangeResult convertAmountByCurrencies(String baseCurrencyCode, String targetCurrencyCode, Double amount) throws SQLException;
}
