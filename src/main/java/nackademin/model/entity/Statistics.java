package nackademin.model.entity;

import nackademin.observer.Observer;
import nackademin.observer.Subject;

import java.util.ArrayList;

public class Statistics  {

    private double roi, net, turn;
    private int won, lose, push;

    public Statistics(double net, double turn, double roi, int won, int lose, int push) {
        this.roi = roi;
        this.net = net;
        this.won = won;
        this.lose = lose;
        this.push = push;
        this.turn = turn;

    }

    public double getTurn() {
        return turn;
    }

    public void setTurn(double turn) {
        this.turn = turn;
    }

    public double getRoi() {
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

}