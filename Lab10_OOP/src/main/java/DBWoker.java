import java.sql.*;

public class DBWoker {
    private final static String URL = "jdbc:mysql://localhost:3306/hunters";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "hunter";
    private Connection connection;
    public DBWoker(){
        try {
            try{
                Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("verno");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
