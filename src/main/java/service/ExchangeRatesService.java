package service;

import DAO.StatementDAO;
import model.Currencies;
import model.ExchangeRates;
import model.ExchangeResult;

import javax.swing.plaf.ColorUIResource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesService implements ExchangeRatesServiceImpl {

    Connection connection;

    StatementDAO DAO;

    public ExchangeRatesService(Connection connection) {
        this.connection = connection;
        this.DAO = new StatementDAO(connection);
    }

    @Override
    public List<ExchangeRates> getAllRates() throws SQLException {

        ResultSet resultSet = DAO.selectAllExchangeRates();
        List<ExchangeRates> eRates = new ArrayList<>();

        while (resultSet.next()) {
            ExchangeRates rate = new ExchangeRates();
            rate.setID(resultSet.getInt("ID"));
            rate.setBaseCurrencyID(resultSet.getInt("BaseCurrencyID"));
            rate.setTargetCurrencyID(resultSet.getInt("TargetCurrencyID"));
            rate.setRate(resultSet.getDouble("Rate"));

            eRates.add(rate);
        }

        return eRates;
    }

    @Override
    public void insertNewExchangeRate(Currencies baseCurrency, Currencies targetCurrency, Double rate) throws SQLException {
        ResultSet correctBaseCurrency = DAO.getCurrency(baseCurrency);
        ResultSet correctTargetCurrency = DAO.getCurrency(targetCurrency);

        baseCurrency.setID(correctBaseCurrency.getInt("ID"));
        baseCurrency.setFullName(correctBaseCurrency.getString("FullName"));
        baseCurrency.setSign(correctBaseCurrency.getString("Sign"));

        targetCurrency.setID(correctTargetCurrency.getInt("ID"));
        targetCurrency.setFullName(correctTargetCurrency.getString("FullName"));
        targetCurrency.setSign(correctTargetCurrency.getString("Sign"));

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setBaseCurrencyID(baseCurrency.getID());
        exchangeRates.setTargetCurrencyID(targetCurrency.getID());
        exchangeRates.setRate(rate);

        DAO.insertNewExchangeRates(exchangeRates);
    }


    @Override
    public ExchangeRates getRate(ExchangeRates currency) throws SQLException {
        return null;
    }

    @Override
    public Currencies selectCurrencyByID(Integer ID) throws SQLException {

        ResultSet resultSet = DAO.selectCurrencyByID(ID);
        Currencies currency = new Currencies();

        while (resultSet.next()) {

            currency.setID(resultSet.getInt("ID"));
            currency.setCode(resultSet.getString("Code"));
            currency.setFullName(resultSet.getString("FullName"));
            currency.setSign(resultSet.getString("Sign"));

        }

        return currency;
    }

    @Override
    public ExchangeRates getRateByCode(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {

        Currencies baseCurrency = new Currencies();
        Currencies targetCurrency = new Currencies();

        baseCurrency.setCode(baseCurrencyCode);
        targetCurrency.setCode(targetCurrencyCode);

        ResultSet baseCurrencyResult = DAO.getCurrency(baseCurrency);
        ResultSet targetCurrencyResult = DAO.getCurrency(targetCurrency);

        Integer baseCurrencyId = baseCurrencyResult.getInt("ID");
        Integer targetCurrencyId = targetCurrencyResult.getInt("ID");


        ResultSet DAOExchangeRate = DAO.selectRateById(baseCurrencyId, targetCurrencyId);

        ExchangeRates resultExchangeRate = new ExchangeRates();

        while (DAOExchangeRate.next()) {
            resultExchangeRate.setID(DAOExchangeRate.getInt("ID"));
            resultExchangeRate.setBaseCurrencyID(DAOExchangeRate.getInt("BaseCurrencyID"));
            resultExchangeRate.setTargetCurrencyID(DAOExchangeRate.getInt("TargetCurrencyId"));
            resultExchangeRate.setRate(DAOExchangeRate.getDouble("Rate"));
        }

        return resultExchangeRate;

    }

    @Override
    public void updateRateByID(Integer rateId, Double rate) {
        System.out.println("service");
        DAO.updateRateByID(rateId, rate);
    }

    @Override
    public ExchangeResult convertAmountByCurrencies(String baseCurrencyCode, String targetCurrencyCode, Double amount) throws SQLException {

        Currencies baseCurrency = new Currencies();
        baseCurrency.setCode(baseCurrencyCode);
        Currencies targetCurrency = new Currencies();
        targetCurrency.setCode(targetCurrencyCode);

        ResultSet baseResult = DAO.getCurrency(baseCurrency);
        ResultSet targetResult = DAO.getCurrency(targetCurrency);

        baseCurrency.setID(baseResult.getInt("ID"));
        baseCurrency.setFullName(baseResult.getString("FullName"));
        baseCurrency.setSign(baseResult.getString("Sign"));

        targetCurrency.setID(targetResult.getInt("ID"));
        targetCurrency.setFullName(targetResult.getString("FullName"));
        targetCurrency.setSign(targetResult.getString("Sign"));

        Double rate, convertedAmount;

        if (getRateByCode(baseCurrencyCode, targetCurrencyCode).getRate() != null) {
            rate = getRateByCode(baseCurrencyCode, targetCurrencyCode).getRate();
            convertedAmount = rate / amount;
        } else if (getRateByCode(targetCurrencyCode, baseCurrencyCode).getRate() != null) {
            rate = getRateByCode(targetCurrencyCode, baseCurrencyCode).getRate();
            convertedAmount = amount / rate;
        } else {
            ExchangeRates baseToUsd = getRateByCode("USD",baseCurrencyCode);
            ExchangeRates targetToUSD = getRateByCode("USD",targetCurrencyCode);
            rate = baseToUsd.getRate() / targetToUSD.getRate();
            convertedAmount = amount * rate;
        }

        return new ExchangeResult(baseCurrency, targetCurrency, rate, amount, convertedAmount);
    }
}
