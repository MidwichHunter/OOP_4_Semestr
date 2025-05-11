import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/Update")
public class Update extends HttpServlet {
    private final static String URL = "jdbc:mysql://localhost:3306/hunters";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "hunter";
    private Connection connection;

    //private static final String filePath = "hunters.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String rang = request.getParameter("rang");
        String group = request.getParameter("group");
        String ageParam = request.getParameter("age");
        int age = ageParam != null && !ageParam.isEmpty() ? Integer.parseInt(ageParam) : 0;
        String pdaNumberParam = request.getParameter("pda_number");
        int pdaNumber = pdaNumberParam != null && !pdaNumberParam.isEmpty() ? Integer.parseInt(pdaNumberParam) : 0;
        PreparedStatement ps = null;
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
            String UPDATE_QUERY = "UPDATE operators SET name = ?, rang = ?, age = ?, `group` = ?, pda_number = ? WHERE id = ?";
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ps = connection.prepareStatement(UPDATE_QUERY);
            ps.setString(1, name);
            ps.setString(2, rang);
            ps.setInt(3, age);
            ps.setString(4, group);
            ps.setInt(5, pdaNumber);
            ps.setInt(6, lastPersonId);
            System.out.println(ps);
            ps.executeUpdate();
            System.out.println("execute is successful");
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