package nackademin.model;

import nackademin.observer.Observer;
import nackademin.observer.Subject;

import java.util.ArrayList;

public class Statistics implements Subject {

    private double roi, net, turn;
    private int won, lose, push;
    private ArrayList<Observer> observers;


    public Statistics(double net, double turn, double roi, int won, int lose, int push) {
        this.roi = roi;
        this.net = net;
        this.won = won;
        this.lose = lose;
        this.push = push;
        this.turn = turn;

        observers = new ArrayList<>();
    }

    public double getTurn() {
        return turn;
    }

    public void setTurn(double turn) {
        this.turn = turn;
    }

    public double getRoi() {
        System.out.println("Roi: " + roi);
        return roi;

    }

    public void setRoi() {
       calculateRoi();
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getPush() {
        return push;
    }

    public void setPush(int push) {
        this.push = push;
    }

    private void calculateRoi() {
        roi = (net /  turn);
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyUpdate() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

}