import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/register"})
public class register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        String birthday = day + "/" + month + "/" + year;

        // Database connection
        String dbUrl = "jdbc:mysql://localhost:3306/logdata";
        String dbUser = "root";
        String dbPass = "";

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
                    String sql = "INSERT INTO register (first_name, last_name, username, password, email, birthday) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setString(3, username);
                    stmt.setString(4, password);
                    stmt.setString(5, email);
                    stmt.setString(6, birthday);

                    int rowsInserted = stmt.executeUpdate();

                    out.println("<!DOCTYPE html>");
                    out.println("<html><head><title>Registration Result</title></head><body>");

                    if (rowsInserted > 0) {
                        out.println("<h1>Registration Successful!</h1>");
                        out.println("<script>alert('Registration Suceess Please Login!');</script>");
                        out.println("<script>window.location.href='loginsnap.html';</script>");
                    } else {
                        out.println("<script>alert('Registration failed Please try again!')");
                    }

                    out.println("</body></html>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h1>Error occurred: " + e.getMessage() + "</h1>");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles Snapchat-style user registration";
    }
}
