package nackademin.model.database;


public abstract class Database {

    public static BetDatabase getBetDatabase() {
        return BetDatabase.getInstance();
    }
    public static UserDatabase getUserDatabase() {
        return UserDatabase.getInstance();
    }
    public static StatisticsDatabase getStatisticsDatabase() {
        return StatisticsDatabase.getInstance();
    }
    protected abstract void sendData();
    protected abstract void fetchData();

}

