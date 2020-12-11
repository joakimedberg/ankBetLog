package nackademin.observer;

import nackademin.model.Bet;

public interface Subject
{
    void attach(Observer o);
    void notifyUpdate();
}