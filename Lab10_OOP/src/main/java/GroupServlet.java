import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
    private final static String URL = "jdbc:mysql://localhost:3306/hunters";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "hunter";
    private Connection connection;
    //private static final String filePath = "hunters.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String rang = request.getParameter("rang");
        int age = Integer.parseInt(request.getParameter("age"));
        String group = request.getParameter("group");
        int pdaNumber = Integer.parseInt(request.getParameter("pda_number"));

        PreparedStatement ps = null;
        String INSERT_NEW = "INSERT INTO operators (name, rang, age, `group`, pda_number) VALUES (?, ?, ?, ?, ?)";
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ps = connection.prepareStatement(INSERT_NEW); // Initialize the PreparedStatement here
            ps.setString(1, name);
            ps.setString(2, rang);
            ps.setInt(3, age);
            ps.setString(4, group);
            ps.setInt(5, pdaNumber);
            System.out.println(ps);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Lab10_OOP_war_exploded");

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DBWoker worker = new DBWoker();

        String query = "Select * from operators";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body style=\"background-color: #0a58ca\"><div><table class=\"table\"><thead><tr><th scope=\"col\">Имя</th><th scope=\"col\">Ранг</th><th scope=\"col\">Возраст</th> <th scope=\"col\">Группировка</th><th scope=\"col\">Номер ПДА</th></tr></thead>");
        try{
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                out.println("<tbody> <tr><td>" + resultSet.getString("name") + "</td><td>" + resultSet.getString("rang") + "</td><td>" + resultSet.getInt("age") + "</td><td>" + resultSet.getString("group") + "</td><td>" + resultSet.getInt("pda_number") + "</td>");
                Hunter hunter = new Hunter(resultSet.getString("name"), resultSet.getString("rang"), resultSet.getInt("age"), resultSet.getString("group"), resultSet.getInt("pda_number"));
                System.out.println(hunter.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
    }
}