package DAO;

import model.Currencies;
import model.ExchangeRates;

import java.sql.*;

public class StatementDAO implements StatmentDAOImpl{

    Connection connection;

    public StatementDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ResultSet selectAllCurrencies() {

        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Currencies;";
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public void insertNewCurrency(Currencies currency) {
        try {
            String sql = "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());
            System.out.println(currency.getCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet getCurrency(Currencies currency) {
        ResultSet resultSet;
        try {
            String sql = "SELECT * FROM Currencies WHERE Currencies.Code = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, currency.getCode());
            System.out.println(currency.getCode());
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public ResultSet selectAllExchangeRates() {
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM ExchangeRates;";
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public ResultSet selectCurrencyByID(Integer ID) {
        ResultSet resultSet;
        try {

            String sql = "SELECT * FROM Currencies WHERE Currencies.ID = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ID);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public ResultSet selectRateById(Integer baseCurrencyId, Integer targetCurrencyId) {
        ResultSet resultSet;
        try {
            String sql = "SELECT * FROM ExchangeRates WHERE ExchangeRates.baseCurrencyId = ? AND ExchangeRates.targetCurrencyId = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, baseCurrencyId.toString());
            statement.setString(2, targetCurrencyId.toString());
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public void insertNewExchangeRates(ExchangeRates exchangeRate) {
        try {
            String sql = "INSERT INTO ExchangeRates (baseCurrencyId, TargetCurrencyId, Rate) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, exchangeRate.getBaseCurrencyID());
            statement.setInt(2, exchangeRate.getTargetCurrencyID());
            statement.setDouble(3, exchangeRate.getRate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRateByID(Integer rateID, Double rate) {
        try {
            String sql = "UPDATE ExchangeRates SET Rate = ? WHERE ExchangeRates.ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, rate);
            statement.setInt(2, rateID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
