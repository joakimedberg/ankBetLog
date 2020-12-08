package nackademin.model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Database {
    private static Database database;
    private Connection connection;
    private User user;
    private Statistics statistics;
    private List<Bet> bets;

    private Database(){
        connect();
        fetchUser();
        fetchStatistics();
        fetchBets();
    }

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private void connect()  {
        try {
            String url = getClass().getResource("/database.db").toString();
            connection = DriverManager.getConnection("jdbc:sqlite:"+url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection to database established.");
    }

    private void fetchUser() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            user = new User(username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    private void fetchStatistics() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM statistics");

            double net = resultSet.getDouble("net");
            double roi = resultSet.getDouble("roi");
            int won = resultSet.getInt("won");
            int lose = resultSet.getInt("lose");
            int push = resultSet.getInt("push");

            statistics = new Statistics(roi, net, won, lose, push);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Statistics getStatistics() {
        return statistics;
    }

    private void fetchBets() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bets");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            bets = new ArrayList();

            while (resultSet.next()) {
                bets.add(new Bet(resultSet.getInt("id"),
                        formatter.parse(resultSet.getString("date")),
                        resultSet.getString("sport"),
                        resultSet.getString("league"),
                        resultSet.getString("team1"),
                        resultSet.getString("team2"),
                        resultSet.getString("period"),
                        resultSet.getString("category"),
                        resultSet.getString("line"),
                        resultSet.getString("bet"),
                        resultSet.getDouble("odds"),
                        resultSet.getDouble("stake"),
                        resultSet.getDouble("net"),
                        resultSet.getString("outcome")));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Database connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}