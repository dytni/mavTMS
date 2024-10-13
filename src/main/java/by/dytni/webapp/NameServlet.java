package by.dytni.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(
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
        resp.setContentType("text/html; charset=UTF-8");

        String body = """
                <!DOCTYPE html>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
                <html lang="en">
                <body>
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                    <div class="container-fluid">
                        <a class="navbar-brand">Names</a>
                    </div>
                </nav>
                <div class="container mt-3">
                <form id="addNameForm">
                    <div class="mb-3">
                        <label for="nameInput" class="form-label">Add New Name:</label>
                        <input type="text" class="form-control" id="nameInput" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Name</button>
                </form>
                <ul class="list-group mt-3">
                """;

        StringBuffer stringBuffer = new StringBuffer(body);
        for (String name : names) {
            stringBuffer.append("<li class=\"list-group-item\">")
                    .append(name)
                    .append("<form onsubmit=\"return deleteName('")
                    .append(name)
                    .append("');\" method=\"post\" class=\"d-inline-block ms-3\">")
                    .append("<button type=\"submit\" class=\"badge bg-danger\">Delete</button></form></li>");
        }

        stringBuffer.append("""
                </ul>
                </div>
                <script>
                function deleteName(name) {
                    fetch('/names/' + name, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            location.reload(); // Reload page to update names list
                        }
                    });
                    return false; // Prevent form submission
                }

                document.getElementById('addNameForm').addEventListener('submit', function(e) {
                    e.preventDefault();
                    const nameInput = document.getElementById('nameInput').value.trim();
                    if (nameInput) {
                        fetch('/names/' + nameInput, {
                            method: 'POST'
                        }).then(response => {
                            if (response.ok) {
                                location.reload(); // Reload page to update names list
                            } else if (response.status === 409) {
                                alert('Name already exists');
                            }
                        });
                    }
                });
                </script>
                </body>
                </html>
                """);

        resp.getWriter().println(stringBuffer);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameToDelete = req.getPathInfo().substring(1);
        if (names.remove(nameToDelete)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(nameToDelete + " removed successfully");
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Name not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newName = req.getPathInfo().substring(1);
        if (names.contains(newName)) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Name already exists");
        } else {
            names.add(newName);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(newName + " added successfully");
        }
    }
}
