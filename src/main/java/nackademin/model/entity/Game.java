package nackademin.model.entity;

public class Game {

    private String date;
    private boolean tbd;
    private String sport, league, team1, team2;

    protected Game(String date, String sport, String league, String team1, String team2) {
        this.date = date;
        this.sport = sport;
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getDate() {
        return date;
    }

    public String getSport() {
        return sport;
    }

    public String getLeague() {
        return league;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }
}