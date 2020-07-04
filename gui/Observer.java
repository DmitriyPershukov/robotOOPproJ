package gui;

import gui.Listener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Observer {
    private static Observer instanse = new Observer();

    private Observer() { }
    static
    {
        instanse = new Observer();
    }
    public static Observer getInstanse() {
        return instanse;
    }
    List<Listener> listener = new ArrayList<>();
    public void notifyModelUpdate(Point modifiedPosition)
    {
        for (Listener listen35 :listener)
        {
            listen35.onModelUpdate(modifiedPosition);
        }
    }
    public void subscribe(Listener nowListener)
    {
        listener.add(nowListener);
    }
}
