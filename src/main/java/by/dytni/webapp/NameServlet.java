package by.dytni.webapp;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet
        (
                name = "NameServlet",
                urlPatterns = "/names/*"
        )
public class NameServlet extends HttpServlet {

    private Set<String> names;

    @Override
    public void init() throws ServletException {
        names = new HashSet<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write(names.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameToDelete = req.getPathInfo().substring(1);
        names.remove(nameToDelete);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(nameToDelete + " removed successfully");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newName = req.getPathInfo().substring(1);
        if (names.contains(newName)) {
            resp.sendError(409);
        }
        names.add(newName);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(newName + " added successfully");
    }

}
