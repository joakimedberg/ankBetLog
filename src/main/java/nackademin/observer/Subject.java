package nackademin.observer;

public interface Subject
{
    void attach(Observer o);
    void notifyUpdate();
}