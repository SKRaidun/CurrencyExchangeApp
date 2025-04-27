package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractExchangeRatesForPatch extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getMethod().equalsIgnoreCase("PATCH")) {
            super.service(request, response);
        } else {
            try {
                doPatch(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract void doPatch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;
}
