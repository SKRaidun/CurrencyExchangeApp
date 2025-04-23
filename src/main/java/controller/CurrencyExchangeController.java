package controller;

import model.Currencies;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("")
public class CurrencyExchangeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/semenraydun/Desktop/CurrencyExchange/src/main/resources/databases/currencies.db");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = null;

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Currencies;";
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        try(PrintWriter printWriter = response.getWriter()) {
            while(resultSet.next()) {
                Currencies curr = new Currencies();
                curr.setID(resultSet.getInt("ID"));
                curr.setCode(resultSet.getString("Code"));
                curr.setFullName(resultSet.getString("FullName"));
                curr.setSign(resultSet.getString("Sign"));

                printWriter.write(curr.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
