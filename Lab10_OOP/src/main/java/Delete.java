import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
    private final static String URL = "jdbc:mysql://localhost:3306/hunters";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "hunter";
    private Connection connection;

    //private static final String filePath = "hunters.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PreparedStatement ps = null;
        String DELETE_QUERY = "DELETE FROM operators WHERE id = ?";
        int lastPersonId = 0;
        String SELECT_LAST_ID_QUERY = "SELECT id FROM operators ORDER BY id DESC LIMIT 1";

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement selectPs = connection.prepareStatement(SELECT_LAST_ID_QUERY);
            ResultSet resultSet = selectPs.executeQuery();

            if (resultSet.next()) {
                lastPersonId = resultSet.getInt("id");
                System.out.println(lastPersonId);
            }
            ps = connection.prepareStatement(DELETE_QUERY);
            ps.setInt(1, lastPersonId);
            ps.executeUpdate();
            System.out.println("Delete operation successful");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        response.sendRedirect("/Lab10_OOP_war_exploded");

    }
}