package nackademin.model;

import java.sql.*;
import java.util.*;

public class Database {
    private static Database database;
    private Connection connection;
    private User user;
    private Statistics statistics;
    private LinkedList<Bet> bets;

    private Database(){
        connect();
        fetchUser();
        fetchStatistics();
        fetchBets();

    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private void connect()  {
        try {
            String url = String.valueOf(getClass().getResource("/database.db"));
            System.out.println(getClass().getResource("/database.db"));
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


            bets = new LinkedList<>();

            while (resultSet.next()) {
                bets.add(new Bet(resultSet.getInt("id"),
                        resultSet.getString("date"),
                        resultSet.getString("sport"),
                        resultSet.getString("league"),
                        resultSet.getString("team1"),
                        resultSet.getString("team2"),
                        resultSet.getString("period"),
                        resultSet.getString("category"),
                        resultSet.getString("bet"),
                        resultSet.getString("line"),
                        resultSet.getDouble("odds"),
                        resultSet.getDouble("stake"),
                        resultSet.getString("net"),
                        resultSet.getString("outcome")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Bet> getBets() {
        return Collections.unmodifiableList(bets);
    }

    public Bet getLatestBet(){
        return bets.getLast();
    }

    private void insertBet() {
        String sql = "INSERT INTO bets('id','date','sport',"
                + "'league', 'team1','team2','period','category'," +
                " 'bet','line', 'odds', 'stake', 'net', 'outcome') VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Bet bet = bets.getLast();
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bet.getId());
            statement.setString(2, bet.getGame().getDate());
            statement.setString(3, bet.getGame().getSport());
            statement.setString(4, bet.getGame().getLeague());
            statement.setString(5, bet.getGame().getTeam1());
            statement.setString(6, bet.getGame().getTeam2());
            statement.setString(7, bet.getPeriod());
            statement.setString(8, bet.getCategory());
            statement.setString(9, bet.getBet());
            statement.setString(10, bet.getLine());
            statement.setDouble(11, bet.getOdds());
            statement.setDouble(12, bet.getStake());
            statement.setString(13, bet.getNet());
            statement.setString(14, bet.getOutcome());
            statement.executeUpdate();
            System.out.println("Bet added to database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addBet(String date, String sport, String league, String team1,
                       String team2, String period, String category, String bet,
                       String line, Double odds, Double stake) {

        bets.add(new Bet(bets.size()+1, date, sport, league, team1, team2,
                period, category, bet, line, odds, stake, "TBD","TBD" ));
        insertBet();
    }

    public void updateNetAndOutcome(Bet bet) {
            insertNetAndOutcome(bet);


    }

    private void insertNetAndOutcome(Bet bet) {
        String sql = "UPDATE bets SET net = '" + bet.getNet() + "' WHERE id=" + bet.getId();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ;
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

