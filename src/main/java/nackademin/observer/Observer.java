package nackademin.observer;

import nackademin.model.Bet;

public interface Observer
{
    void update(Bet bet);
}