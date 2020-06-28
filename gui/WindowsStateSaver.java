package gui;

import javax.swing.*;
import javax.swing.text.Position;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class WindowsStateSaver {
    private JInternalFrame[] frames;
    public WindowsStateSaver(JInternalFrame[] ames) {
        this.frames = ames;
    }
    public void saveWindowState() throws IOException {
        File json = new File("windows.json");
        ArrayList<windowState> windowList = new ArrayList<>();
        for (int i = 0; i < frames.length; i++) {
            windowState window = new windowState();
            window.width = (frames[i].getWidth());
            window.height = (frames[i].getHeight());
            window.x = (frames[i].getX());
            window.y = (frames[i].getY());
            window.isIcon = (frames[i].isIcon());
            window.isMaxed = (frames[i].isMaximum());

            windowList.add(window);
        }
        Gson jsonConvert = new Gson();
        var jsonOutput = jsonConvert.toJson(windowList);
        byte[] byteResult = jsonOutput.getBytes();
        FileOutputStream fileOutput = new FileOutputStream(json);
        fileOutput.write(byteResult, 0, byteResult.length);
    }

    public void restoreWindowsState() throws IOException, PropertyVetoException {
        Gson jsonConverter = new Gson();
        Type listType = new TypeToken<ArrayList<windowState>>(){}.getType();
        byte[] states = Files.readAllBytes(Paths.get("windows.json"));
        ArrayList<windowState> statesData =jsonConverter.fromJson(new String(states), listType);
        for(int i = 0; i < frames.length; i++)
        {
            for (int j =0; j< statesData.size(); j++)
            {
                if(i != j)
                    continue;
                frames[i].setBounds(statesData.get(j).x, statesData.get(j).y, statesData.get(j).width, statesData.get(j).height);
                frames[i].setIcon(statesData.get(j).isIcon);
                frames[i].setMaximum(statesData.get(j).isMaxed);
            }
        }
    }
}
