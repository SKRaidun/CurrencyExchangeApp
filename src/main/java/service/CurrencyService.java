package service;

import DAO.StatementDAO;
import model.Currencies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyService implements CurrencySeviceImpl {

    Connection connection;

    StatementDAO DAO;

    public CurrencyService(Connection connection) {
        this.connection = connection;
        DAO = new StatementDAO(connection);
    }


    @Override
    public List<Currencies> getAllCurrencies() throws SQLException {

        ResultSet resultSet = DAO.selectAllCurrencies();
        List<Currencies> currencies = new ArrayList<>();

        while(resultSet.next()) {
            Currencies currency = new Currencies();
            currency.setID(resultSet.getInt("ID"));
            currency.setCode(resultSet.getString("Code"));
            currency.setFullName(resultSet.getString("FullName"));
            currency.setSign(resultSet.getString("Sign"));

            currencies.add(currency);
        }

        return currencies;
    }

    @Override
    public void insertNewCurrency(Currencies currency) {
        DAO.insertNewCurrency(currency);
    }

    @Override
    public Currencies getCurrency(Currencies currency) throws SQLException {
        ResultSet resultSet = DAO.getCurrency(currency);
        Currencies newCurrency = new Currencies();

        newCurrency.setID(resultSet.getInt("ID"));
        newCurrency.setCode(resultSet.getString("Code"));
        newCurrency.setFullName(resultSet.getString("FullName"));
        newCurrency.setSign(resultSet.getString("Sign"));


        return newCurrency;
    }


}
