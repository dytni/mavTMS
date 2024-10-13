package by.dytni.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "LoginServlet", urlPatterns = "/login/*")
public class LoginServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/users_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1331";
    private static final String SELECT = "SELECT * FROM user WHERE username = ?;";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println("yo");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String username = req.getParameter("username");
        final String password = req.getParameter("password");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Инициализация драйвера
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Подготовка запроса
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            // Проверка существования пользователя
            if (resultSet.next()) {
                String foundPassword = resultSet.getString("password");

                if (password.equals(foundPassword)) {
                    // Успешный вход
                    resp.sendRedirect("/names");
                } else {
                    // Неправильный пароль
                    resp.sendRedirect("/index.jsp");
                }
            } else {
                // Пользователь не найден
                resp.sendRedirect("/main");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(500, "Internal Server Error");
        } finally {
            // Закрываем ресурсы
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
