package nackademin.model;

public class Statistics {

    private double roi, net;
    private int won, lose, push;


    protected Statistics(double roi, double net, int won, int lose, int push) {
        this.roi = roi;
        this.net = net;
        this.won = won;
        this.lose = lose;
        this.push = push;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
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
}