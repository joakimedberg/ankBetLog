package nackademin.model;

import java.util.Date;
public class Bet {

    private int id;
    private String outcome, bet, period, category, line, net;
    private Double odds, stake;
    private Game game;

    protected Bet(int id, String date, String sport, String league, String team1, String team2, String period, String category, String bet,String line, Double odds, Double stake, String net,String outcome) {

        game = new Game(date, sport, league, team1, team2);

        this.id = id;
        this.odds = odds;
        this.stake = stake;
        this.bet = bet;
        this.period = period;
        this.category = category;
        this.line = line;
        this.net = net;
        this.outcome = outcome;
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

    private void calculateNet() {
        System.out.println(outcome);
        if (outcome.equals("PUSH")) {
            net = String.valueOf(0);
        } else if (outcome.equals("WIN")) {
            net = String.valueOf(odds * stake);
        } else if (outcome.equals("1/2WIN")) {
            net = String.valueOf((odds * stake) / 2);
        } else if (outcome.equals("LOSS")) {
            net = String.valueOf(-stake);
        } else if (outcome.equals("1/2LOSS")) {
            net = String.valueOf(- (stake/2));
        }


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

    public String getNet() {
        return net;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
        calculateNet();
    }

    public String getBet() {
        return bet;
    }
}