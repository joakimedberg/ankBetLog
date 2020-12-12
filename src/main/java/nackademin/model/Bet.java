package nackademin.model;

import nackademin.observer.Observer;
import nackademin.observer.Subject;
import java.util.ArrayList;

public class Bet implements Subject, Cloneable {

    private int id;
    private String outcome, bet, period, category, line, net;
    private Double odds, stake;
    private Game game;
    private ArrayList<Observer> observers;

    public Bet(int id, String date, String sport, String league, String team1, String team2, String period, String category, String bet, String line, Double odds, Double stake, String net, String outcome, boolean tbd) {
        game = new Game(date, sport, league, team1, team2, tbd);

        this.id = id;
        this.odds = odds;
        this.stake = stake;
        this.bet = bet;
        this.period = period;
        this.category = category;
        this.line = line;
        this.net = net;
        this.outcome = outcome;

        observers = new ArrayList<>();

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
            net = String.valueOf(0);
        } else if (outcome.equals("WIN")) {
            net = String.valueOf((odds * stake) - stake);
        } else if (outcome.equals("1/2WIN")) {
            net = String.valueOf(((odds * stake) / 2) - stake);
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
        if (!outcome.equals("void")){
            this.outcome = outcome;
            game.setTbd(false);
            calculateNet();
        } else if (outcome.equals("void") & game.isTbd()) {

        }


        notifyUpdate();
    }

    public String getBet() {
        return bet;
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
    @Override
    public Object clone()
            throws CloneNotSupportedException
    {
        return super.clone();
    }
}