package nackademin.model.database;

import nackademin.model.enteties.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDatabase extends Database{
    private User user;

    public UserDatabase(){
        System.out.println("New User Database connection established.");
        fetchData();
    }

    @Override
    protected void sendData() {
            // not implemented yet
    }

    @Override
    protected void fetchData() {
        try {
            Statement statement = SQLiteConnection.getInstance().connect().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            user = new User(username, password);

            SQLiteConnection.getInstance().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }
}
