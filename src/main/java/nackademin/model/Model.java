package nackademin.model;

import nackademin.model.database.BetDatabase;
import nackademin.model.database.StatisticsDatabase;
import nackademin.model.database.UserDatabase;

public class Model {

    private BetDatabase betDatabase;
    private UserDatabase userDatabase;
    private StatisticsDatabase statisticsDatabase;

    public Model() {
        betDatabase = new BetDatabase();
        userDatabase = new UserDatabase();
        statisticsDatabase = new StatisticsDatabase();
        betDatabase.setStatisticsDatabase(statisticsDatabase);

    }

    public BetDatabase getBetDatabase() {
        return betDatabase;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public StatisticsDatabase getStatisticsDatabase() {
        return statisticsDatabase;
    }
}
