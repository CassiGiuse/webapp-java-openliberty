package com;

import java.io.IOException;

import javax.naming.InitialContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    try {
      Context ctx = new InitialContext();
      DataSource ds = (DataSource) ctx.lookup("jdbc/sqlDataSource");
      Connection cnn = ds.getConnection();
      Statement s = cnn.createStatement();
      ResultSet r = s.executeQuery("SHOW TABLES;");

      StringBuilder re = new StringBuilder();
      while (r.next()) {
        String columnName = r.getString(1);
        re.append(columnName);
        re.append("\n");
      }

      resp.getWriter().printf("Connessione riuscita! Tabelle:\n%s", re);

    } catch (Exception e) {
      resp.getWriter().printf("Connessione non riuscita! errore:\n%s", e.getMessage());
    }
  }

}
