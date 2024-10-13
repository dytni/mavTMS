package by.dytni.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "MainServlet",
        urlPatterns = "/main"
)
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setContentType("text/html; charset=UTF-8");
        final String body = """
                <!DOCTYPE html>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
                <html lang="en">
                <body>
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                    <div class="container-fluid">
                        <a class="navbar-brand">My Homeworks</a>
                    </div>
                </nav>
                <div class="dropdown" >
                    <button class="btn btn-secondary dropdown-toggle" style="width: 100%; height: 10%;" type="button" data-bs-toggle="dropdown" aria-expanded="false">My tasks</button>
                    <ul class="dropdown-menu" style="width: 100%;">
                        <li><button type="button"  onclick="location.href='time'" class="dropdown-item">time</button></li>
                        <li><button type="button" onclick="location.href='index.jsp'" class="dropdown-item">name</button></li>
                    </ul>
                </div>
                </body>
                </html>
                """;
        resp.getWriter().println(body);
    }
}

