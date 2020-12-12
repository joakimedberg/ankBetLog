package nackademin.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static SQLiteConnection sqLiteConnection;
    private Connection connection;

    private SQLiteConnection(){}

    protected static SQLiteConnection getInstance() {
        if (sqLiteConnection == null) {
            sqLiteConnection = new SQLiteConnection();
        }
        return sqLiteConnection;
    }

    protected Connection connect()  {
        try {
            String url = String.valueOf(getClass().getResource("/database.db"));
            connection = DriverManager.getConnection("jdbc:sqlite:"+url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection to database established.");
        return connection;
    }

    protected void close() {
        try {
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
