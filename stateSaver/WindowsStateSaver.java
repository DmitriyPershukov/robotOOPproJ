package stateSaver;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import stateSaver.WindowState;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WindowsStateSaver {
    private JInternalFrame[] frames;
    public WindowsStateSaver(JInternalFrame[] ames) {
        this.frames = ames;
    }
    public void saveWindowState() throws IOException {
        File json = new File("windows.json");
        ArrayList<WindowState> windowList = new ArrayList<>();
        for (int i = 0; i < frames.length; i++) {
            WindowState window = new WindowState();
            window.width = (frames[i].getWidth());
            window.windowName = frames[i].getName();
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
        Type listType = new TypeToken<ArrayList<WindowState>>(){}.getType();
        byte[] states = Files.readAllBytes(Paths.get("windows.json"));
        ArrayList<WindowState> statesData =jsonConverter.fromJson(new String(states), listType);
        for(int i = 0; i < frames.length; i++)
        {
            for (int j =0; j< statesData.size(); j++) {
                String winName = statesData.get(j).windowName;
                if (winName.equals(frames[i].getName())) {
                    frames[i].setBounds(statesData.get(j).x, statesData.get(j).y, statesData.get(j).width, statesData.get(j).height);
                    frames[i].setIcon(statesData.get(j).isIcon);
                    frames[i].setMaximum(statesData.get(j).isMaxed);
                }
            }
        }
    }
}
