package nackademin.model.database;

import nackademin.model.entity.Bet;
import nackademin.observer.Observer;

import java.sql.*;
import java.util.LinkedList;

public class BetDatabase extends Database implements Observer {
    private StatisticsDatabase statisticsDatabase;
    private LinkedList<Bet> bets;
    private Bet bet;

    public BetDatabase() {
        System.out.println("New Bet Database connection established.");
        bets = new LinkedList<>();
    }

    @Override
    protected void sendData() {
        String sql = "INSERT or REPLACE INTO bets('id','date','sport',"
                + "'league', 'team1','team2','period','category'," +
                " 'bet','line', 'odds', 'stake', 'net', 'outcome', 'tbd', 'voided') VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = SQLiteConnection.getInstance().connect().prepareStatement(sql);
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
            if (bet.getNet() != null)
                statement.setDouble(13, bet.getNet());
            statement.setString(14, bet.getOutcome());
            statement.setBoolean(15, bet.isTbd());
            statement.setBoolean(16, bet.isVoided());
            statement.executeUpdate();
            System.out.println("Bet added to database.");
            SQLiteConnection.getInstance().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fetchData() {
        try {
            Statement statement = SQLiteConnection.getInstance().connect().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bets ORDER BY id DESC");

            bets = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (bets.isEmpty() || bets.stream().noneMatch(bet -> bet.getId() == id)) {
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
                            resultSet.getDouble("net"),
                            resultSet.getString("outcome"),
                            resultSet.getBoolean("tbd"),
                            resultSet.getBoolean("voided")
                            )
                    );

                    bets.getLast().attach(this);
                    bets.getLast().attach(statisticsDatabase);
                }
            }

            System.out.println("Bets fetched from database.");
            SQLiteConnection.getInstance().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Bet> getBets() {
        fetchData();
        return bets;
    }

    @Override
    public void update(Object o) {
        bet = (Bet) o;
        sendData();
    }

    public void setStatisticsDatabase(StatisticsDatabase statisticsDatabase) {
        this.statisticsDatabase = statisticsDatabase;
    }
}
