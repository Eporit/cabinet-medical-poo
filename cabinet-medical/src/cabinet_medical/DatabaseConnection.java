package cabinet_medical;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	  private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	    private static final String USER = "cabinet_medical";
	    private static final String PASSWORD = "yudl6esti3abew1lthu6";

	    private Connection connection;

	    public DatabaseConnection() {
	        try {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public Connection getConnection() {
	        return connection;
	    }

	    public ResultSet executeQuery(String query) {
	        try {
	            Statement statement = connection.createStatement();
	            return statement.executeQuery(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    public int executeUpdate(String query) {
	        try {
	            Statement statement = connection.createStatement();
	            return statement.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }

	    public void close() {
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
