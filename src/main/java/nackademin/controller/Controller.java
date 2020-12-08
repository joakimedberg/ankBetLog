package nackademin.controller;

import nackademin.model.Database;
import nackademin.view.View;

public abstract class Controller {

    private Database db;

    public Controller() {
        db = Database.getDatabase();
    }

    public Database getDatabase() {
        return db;
    }
    public abstract void setView(View view);

}