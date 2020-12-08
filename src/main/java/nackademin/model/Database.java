package nackademin.model;

import java.util.List;

public class Database {
    private static Database database;
    private User user;
    private Statistics statistics;
    private List<Bet> bets;

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public User getUser() {
        return user;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public List<Bet> getBets() {
        return bets;
    }


}