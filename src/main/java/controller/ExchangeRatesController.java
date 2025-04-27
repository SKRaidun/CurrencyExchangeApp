package controller;

import model.Currencies;
import model.ExchangeRates;
import service.CurrencyService;
import service.ExchangeRatesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRatesController extends HttpServlet {

    Connection connection = DBConnection.getConnection();
    ExchangeRatesService service = new ExchangeRatesService(connection);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<ExchangeRates> result;

        try {
            result = service.getAllRates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try(PrintWriter printWriter = response.getWriter()) {
            for (ExchangeRates exchangeRates : result) {
                Integer baseCurrencyId = exchangeRates.getBaseCurrencyID();
                Integer targetCurrencyID = exchangeRates.getTargetCurrencyID();
                Currencies baseCurrency = service.selectCurrencyByID(baseCurrencyId);
                Currencies targetCurrency = service.selectCurrencyByID(targetCurrencyID);

                printWriter.write(exchangeRates.toString(baseCurrency, targetCurrency));
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ExchangeRates exchangeRates = new ExchangeRates();

        Currencies baseCurrency = new Currencies();
        Currencies targetCurrency = new Currencies();

        String baseCurrencyCode = (String) request.getParameter("baseCurrencyCode");
        String targetCurrencyCode = (String) request.getParameter("targetCurrencyCode");
        Double rate  = Double.valueOf(request.getParameter("rate"));

        baseCurrency.setCode(baseCurrencyCode);
        targetCurrency.setCode(targetCurrencyCode);

        try {
            service.insertNewExchangeRate(baseCurrency, targetCurrency, rate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ExchangeRates resultExchangeRate = null;

        try {
            resultExchangeRate = service.getRateByCode(baseCurrencyCode, targetCurrencyCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Integer baseCurrencyID = resultExchangeRate.getBaseCurrencyID();
        Integer targetCurrencyID = resultExchangeRate.getTargetCurrencyID();


        try {
            System.out.println(resultExchangeRate.toString(service.selectCurrencyByID(baseCurrencyID), service.selectCurrencyByID(targetCurrencyID)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter printWriter = response.getWriter()) {

            printWriter.write(resultExchangeRate.toString(service.selectCurrencyByID(baseCurrencyID), service.selectCurrencyByID(targetCurrencyID)));

        } catch (NullPointerException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
