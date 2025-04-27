package service;

import model.Currencies;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CurrencySeviceImpl {
    List<Currencies> getAllCurrencies() throws SQLException;
    void insertNewCurrency(Currencies currency);
    Currencies getCurrency(Currencies currency) throws SQLException;
}
