package control;

import stats.Statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class FanControl {
    private final Statistics stats;
    private LoadConfig cfg;

    private int currentTemp;
    private ArrayList<Integer> tempThresholds;
    private ArrayList<Integer> fSpeedThresholds;
    private ArrayList<Vector<Integer>> thresholds;



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
        tempThresholds = cfg.GetTempThresholds();
        fSpeedThresholds = cfg.GetFSpeedThresholds();
        SmoothFanTransition();
    }

    private void SmoothFanTransition() {
        System.out.println("Smoothing fan transitions...");
        int numOfVals = (tempThresholds.size() - 1) * 4;
        int[] tempArr = new int[numOfVals];
        int[] fSpeedArr = new int[numOfVals];
        int arrListIndex = 0;

        for (int i = 0; i < numOfVals - 1; i = i+4) {
            tempArr[i] = tempThresholds.get(arrListIndex);
            tempArr[i+2] = (tempThresholds.get(arrListIndex+1) + tempArr[i]) / 2;
            tempArr[i+1] = (tempArr[i] + tempArr[i+2]) / 2;
            tempArr[i+3] = (tempThresholds.get(arrListIndex+1) + tempArr[i+2]) / 2;

            fSpeedArr[i] = fSpeedThresholds.get(arrListIndex);
            fSpeedArr[i+2] = (fSpeedThresholds.get(arrListIndex+1) + fSpeedArr[i]) / 2;
            fSpeedArr[i+1] = (fSpeedArr[i] + fSpeedArr[i+2]) / 2;
            fSpeedArr[i+3] = (fSpeedThresholds.get(arrListIndex+1) + fSpeedArr[i+2]) / 2;
            arrListIndex++;
        }

        tempArr[numOfVals - 1] = tempThresholds.get(tempThresholds.size() - 1);
        fSpeedArr[numOfVals - 1] = fSpeedThresholds.get(fSpeedThresholds.size() - 1);

        thresholds = new ArrayList<>();

        for (int i = 0; i < tempArr.length; i++) {
            //System.out.println(tempArr[i] + ", " + fSpeedArr[i]);
            thresholds.add(new Vector<Integer>(tempArr[i], fSpeedArr[i]));
        }
    }

    private void UpdateFanSpeed() {

    }

    private void DetermineCorrectSpeed() {

    }

    public void update() {
        if (cfg.GetGPUFanControlState().equals("0")) return;
        currentTemp = stats.GetCurrentTemp();
        UpdateFanSpeed();
    }
}
