package nackademin.model;

public class Game {

    private String date;
    private boolean tbd;
    private String sport, league, team1, team2;

    protected Game(String date, String sport, String league, String team1, String team2, boolean tbd) {
        this.date = date;
        this.sport = sport;
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
        this.tbd = tbd;
    }

    public boolean isTbd() {
        return tbd;
    }

    public void setTbd(boolean tbd) {
        this.tbd = tbd;
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