package nackademin.model.entity;

import nackademin.observer.Observer;
import nackademin.observer.Subject;
import java.util.ArrayList;

public class Bet implements Subject{

    private final int  id;
    private String outcome, bet, period, category, line;
    private Double odds, stake, net;
    private Game game;
    private boolean tbd, voided;
    private ArrayList<Observer> observers;

    public Bet(int id, String date, String sport, String league, String team1, String team2, String period,
               String category, String bet, String line, Double odds, Double stake, Double net, String outcome,
               boolean tbd, boolean voided) {
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
        this.tbd = tbd;
        this.voided = voided;

        observers = new ArrayList<>();

    }

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
        notifyUpdate();
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
        if (outcome.equals("PUSH")) {
            net = 0.0;
        } else if (outcome.equals("WIN")) {
            net = (odds * stake) - stake;
        } else if (outcome.equals("1/2WIN")) {
            net = (odds * stake) - stake;
            net = net / 2;
        } else if (outcome.equals("LOSS")) {
            net = -stake;
        } else if (outcome.equals("1/2LOSS")) {
            net = - (stake/2);
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

    public Double getNet() {
        return net;
    }

    private void setNet() {
        calculateNet();
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
        setNet();
        setTbd(false);
    }

    public String getBet() {
        return bet;
    }

    public boolean isTbd() {
        return tbd;
    }

    private void setTbd(boolean tbd) {
        this.tbd = tbd;
        notifyUpdate();
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);

        System.out.println("Observer added to Bet.");
    }

    @Override
    public void notifyUpdate() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

}