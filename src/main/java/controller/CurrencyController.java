package controller;

import model.Currencies;
import service.CurrencyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;

@WebServlet(urlPatterns = {"/currencies"})
public class CurrencyController extends HttpServlet {

    Connection connection = DBConnection.getConnection();
    CurrencyService service = new CurrencyService(connection);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Currencies> result;

        try {
            result = service.getAllCurrencies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try(PrintWriter printWriter = response.getWriter()) {
                for (Currencies  currency : result) {
                printWriter.write(currency.toString());
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Currencies currency = new Currencies();


        String name = (String) request.getParameter("name");
        String code = (String) request.getParameter("code");
        String sign = (String) request.getParameter("sign");

        currency.setID(null);
        System.out.println(name);
        currency.setFullName(name);
        currency.setCode(code);
        currency.setSign(sign);

        service.insertNewCurrency(currency);

        Currencies newCurrency;
        try {
            newCurrency = service.getCurrency(currency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try(PrintWriter printWriter = response.getWriter()) {

            printWriter.write(newCurrency.toString());

            System.out.println(newCurrency);

            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        }

    }


