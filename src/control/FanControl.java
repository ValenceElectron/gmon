package control;

import stats.Statistics;

import java.io.IOException;
import java.util.ArrayList;

public class FanControl {
    private final Statistics stats;
    private LoadConfig cfg;

    private int currentTemp;
    private ArrayList<Integer> tempThresholds;
    private ArrayList<Integer> fSpeedThresholds;



    public FanControl(Statistics stats) {
        this.stats = stats;

        // Try/Catch here so that way the error doesn't propagate
        try {
            cfg = new LoadConfig();
        } catch (IOException e) {
           System.out.println("Could not load config.");
           e.printStackTrace();
        }

        init();
    }

    private void init() {
        if (cfg.GetGPUFanControlState().equals("0")) return;
    }

    public void update() {
        if (cfg.GetGPUFanControlState().equals("0")) return;
    }
}
