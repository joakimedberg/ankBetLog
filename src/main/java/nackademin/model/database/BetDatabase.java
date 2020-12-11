package nackademin.model.database;

import nackademin.model.Bet;
import nackademin.observer.Observer;

import java.sql.*;
import java.util.LinkedList;

public class BetDatabase extends Database implements Observer {
    private static BetDatabase betDatabase;
    private LinkedList<Bet> bets;
    private Bet bet;

    private BetDatabase() {
        System.out.println("New Bet Database connection established.");
        bets = new LinkedList<>();
        fetchData();
    }

    protected static BetDatabase getInstance() {
        if (betDatabase == null) {
            betDatabase = new BetDatabase();
        }
        return betDatabase;
    }

    @Override
    protected void sendData() {
        String sql = "INSERT or REPLACE INTO bets('id','date','sport',"
                + "'league', 'team1','team2','period','category'," +
                " 'bet','line', 'odds', 'stake', 'net', 'outcome') VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            statement.setString(13, bet.getNet());
            statement.setString(14, bet.getOutcome());
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

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (bets.isEmpty() || bets.stream().noneMatch(bet -> {
                    if (bet.getId() == id)
                        return true;

                    return false;
                })) {
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
                    System.out.println("hej " + bets.getLast().getId());
                    bets.getLast().attach(this);
                }
            }

            for (Bet b : bets) {
                System.out.println(b.getId());
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
    public void update(Bet bet) {
        this.bet = bet;
        sendData();
    }
}
