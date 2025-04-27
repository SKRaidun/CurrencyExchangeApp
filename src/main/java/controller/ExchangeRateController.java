package controller;

import model.ExchangeRates;
import service.ExchangeRatesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateController extends AbstractExchangeRatesForPatch {

    Connection connection = DBConnection.getConnection();
    ExchangeRatesService service = new ExchangeRatesService(connection);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getPathInfo();
        String rates = null;

        if (path != null) {
            rates = path.split("/")[1];
        }



        String baseCurrency = null;
        String targetCurrency = null;

        if (rates != null) {
            baseCurrency = rates.substring(0, 3);
            targetCurrency = rates.substring(3, 6);
        } else {
        }

        ExchangeRates resultExchangeRate = null;

        try {
            resultExchangeRate = service.getRateByCode(baseCurrency, targetCurrency);
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

    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = request.getReader().readLine();

        String path = request.getPathInfo();
        String rates = null;

        if (path != null) {
            rates = path.split("/")[1];
        }


        String baseCurrency = null;
        String targetCurrency = null;

        if (rates != null) {
            baseCurrency = rates.substring(0, 3);
            targetCurrency = rates.substring(3, 6);
        } else {
        }

        Double rate  = Double.valueOf(body.split("=")[1]);


        ExchangeRates exchangeRate;
        try {
            exchangeRate = service.getRateByCode(baseCurrency, targetCurrency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Integer rateId = exchangeRate.getID();


        service.updateRateByID(rateId, rate);

        try {
            exchangeRate = service.getRateByCode(baseCurrency, targetCurrency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Integer baseCurrencyId = exchangeRate.getBaseCurrencyID();
        Integer targetCurrencyId = exchangeRate.getTargetCurrencyID();


        try (PrintWriter printWriter = response.getWriter()) {

            printWriter.write(exchangeRate.toString(service.selectCurrencyByID(baseCurrencyId), service.selectCurrencyByID(targetCurrencyId)));

        } catch (NullPointerException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
