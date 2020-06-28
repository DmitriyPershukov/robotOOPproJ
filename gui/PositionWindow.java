package gui;

import javax.swing.*;
import java.awt.*;

public class PositionWindow extends JInternalFrame implements Listener {
    private TextArea text;

    public PositionWindow() {
        super("Координаты", false, true, false);
        text = new TextArea("");
        text.setSize(this.getSize());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(text, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        Observer.subscribe(this);
    }

    public void onModelUpdate(Point modifiedPosition)
    {
        text.setText(String.format("X=%s\nY=%s", modifiedPosition.x, modifiedPosition.y));
        text.invalidate();
    }

}

