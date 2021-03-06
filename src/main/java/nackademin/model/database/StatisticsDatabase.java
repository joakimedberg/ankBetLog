package nackademin.model.database;

import nackademin.model.entity.Bet;
import nackademin.model.entity.Statistics;
import nackademin.observer.Observer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatisticsDatabase extends Database implements Observer {
    private Statistics statistics;
    private Bet bet;

    public StatisticsDatabase() {
        System.out.println("New Statistics Database connection established.");
    }
    @Override
    protected void sendData() {
        String sql = "UPDATE statistics SET net = ? , turn = ? , roi = ?, won = ?, lose = ?, push = ?";

        try {
            PreparedStatement statement = SQLiteConnection.getInstance().connect().prepareStatement(sql);

            statement.setDouble(1, statistics.getNet());
            statement.setDouble(2, statistics.getTurn());
            statement.setDouble(3, statistics.getRoi());
            statement.setInt(4, statistics.getWon());
            statement.setInt(5, statistics.getLose());
            statement.setInt(6, statistics.getPush());
            statement.executeUpdate();
            System.out.println("Statistics updated in database.");
            SQLiteConnection.getInstance().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fetchData() {
        try {
            Statement statement = SQLiteConnection.getInstance().connect().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM statistics");

            double net = resultSet.getDouble("net");
            double turn = resultSet.getDouble("turn");
            double roi = resultSet.getDouble("roi");
            int won = resultSet.getInt("won");
            int lose = resultSet.getInt("lose");
            int push = resultSet.getInt("push");

            statistics = new Statistics(net,turn,  roi, won, lose, push);

            SQLiteConnection.getInstance().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statistics getStatistics() {
        fetchData();
        return statistics;
    }

    @Override
    public void update(Object o) {
        bet = (Bet) o;
        if (bet.isTbd())
            return;
        if (bet.isVoided() && !bet.isTbd()) {
            statistics.setNet(statistics.getNet() - bet.getNet());
            statistics.setTurn(statistics.getTurn() - bet.getStake());
            statistics.setRoi();
            if (bet.getOutcome().contains("W")) {
                statistics.setWon(statistics.getWon() - 1);
            } else if (bet.getOutcome().contains("L")) {
                statistics.setLose(statistics.getLose() - 1);
            } else if (bet.getOutcome().contains("P")) {
                 statistics.setPush(statistics.getPush() - 1);
            }
        } else {
            statistics.setNet(statistics.getNet() + bet.getNet());
            statistics.setTurn(statistics.getTurn() + bet.getStake());
            statistics.setRoi();
            if (bet.getOutcome().contains("W")) {
                statistics.setWon(statistics.getWon() + 1);
            } else if (bet.getOutcome().contains("L")) {
                statistics.setLose(statistics.getLose() + 1);
            } else if (bet.getOutcome().contains("P")) {
                statistics.setPush(statistics.getPush() + 1);
            }
        }

        sendData();
    }
}