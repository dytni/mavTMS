package by.dytni.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(
        name = "Main",
        urlPatterns = "/time"

)
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String zone = req.getParameter("zone");
        ZoneId zoneId;
        if (zone != null) {
            zoneId = ZoneId.of(zone);
        }else {
            zoneId = ZoneId.systemDefault();
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        String time = zonedDateTime.format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        resp.getWriter().println(time);
    }

}
