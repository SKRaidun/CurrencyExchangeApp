package controller;

import model.Currencies;
import service.CurrencyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/currency/*"})
public class AnotherCurrencyController extends HttpServlet {

    Connection connection = DBConnection.getConnection();
    CurrencyService service = new CurrencyService(connection);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Currencies currency = new Currencies();

        String path = request.getPathInfo();
        String code = null;

        if (path != null) {
            code = path.split("/")[1];
        }

        currency.setCode(code);

        Currencies newCurrency;

        try {
            newCurrency = service.getCurrency(currency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter printWriter = response.getWriter()) {

            printWriter.write(newCurrency.toString());

        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
