package gui;

import gui.Listener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Observer {
    static List<Listener> listener = new ArrayList<>();
    public static void notifyModelUpdate(Point modifiedPosition)
    {
        for (Listener listen35 :listener)
        {
            listen35.onModelUpdate(modifiedPosition);
        }
    }
    public static void subscribe(Listener nowListener)
    {
        listener.add(nowListener);
    }
}
