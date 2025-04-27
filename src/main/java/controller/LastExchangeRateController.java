package controller;

import model.Currencies;
import model.ExchangeRates;
import model.ExchangeResult;
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

@WebServlet(urlPatterns = "/exchange")
public class LastExchangeRateController extends HttpServlet {

    Connection connection = DBConnection.getConnection();
    ExchangeRatesService service = new ExchangeRatesService(connection);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String baseCurrencyCode = request.getParameter("from");
        String targetCurrencyCode = request.getParameter("to");
        Double amount = Double.valueOf(request.getParameter("amount"));

        Double convertedAmount = null;
        ExchangeResult result = null;

        try {
            result = service.convertAmountByCurrencies(baseCurrencyCode, targetCurrencyCode, amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try(PrintWriter printWriter = response.getWriter()) {
                printWriter.write(String.valueOf(result));
            }

    }
}
