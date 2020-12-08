package nackademin.model;

import java.util.Date;
public class Bet {

    private int id;
    private String outcome, bet, period, category, line;
    private double odds, stake, net;
    private Game game;

    public Bet(int id, Date date, String sport, String league, String team1, String team2, String period, String category, String bet,String line, double odds, double stake, double net, String outcome) {

        game = new Game(id, date, sport, league, team1, team2);

        this.id = id;
        this.odds = odds;
        this.stake = stake;
        this.net = net;
        this.bet = bet;
        this.outcome = outcome;
        this.period = period;
        this.category = category;
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public String getCategory() {
        return category;
    }

    public String getPeriod() {
        return period;
    }

    public Game getGame() {
        return game;
    }

    public void calculateNet() {
        net = odds * stake;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public int getId() {
        return id;
    }

    public double getOdds() {
        return odds;
    }

    public double getStake() {
        return stake;
    }

    public double getNet() {
        return net;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getBet() {
        return bet;
    }
}