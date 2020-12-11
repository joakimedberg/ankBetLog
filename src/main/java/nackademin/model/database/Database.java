package nackademin.model.database;


public abstract class Database {

    public static BetDatabase getBetDatabase() {
        return BetDatabase.getInstance();
    }
    public static UserDatabase getUserDatabase() {
        return UserDatabase.getInstance();
    }

    protected abstract void sendData();
    protected abstract void fetchData();

}

