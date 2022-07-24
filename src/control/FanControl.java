package control;

import stats.Statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class FanControl {
    private final Statistics stats;
    private LoadConfig cfg;

    final private String userName = System.getProperty("user.name");
    final private String workingDir = "/home/" + userName + "/.local/bin/gmon_parser";
    final private String fanCon = workingDir+"/src/scripts/fanCon";

    private int currentTemp;
    private ArrayList<Integer> tempThresholds;
    private ArrayList<Integer> fSpeedThresholds;
    private ArrayList<int[]> thresholds;



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
            System.out.println(tempArr[i] + ", " + fSpeedArr[i]);
            thresholds.add(new int[2]);
            thresholds.get(i)[0] = tempArr[i];
            thresholds.get(i)[1] = fSpeedArr[i];
        }
    }

    private void UpdateFanSpeed() {
        int index = DetermineCorrectFSpeed();

        Process p;
        try{
            String[] cmd = {"sh", fanCon, "1", Integer.toString(thresholds.get(index)[1])};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int DetermineCorrectFSpeed() {
        for (int i = 0; i < thresholds.size(); i++) {
            if (currentTemp <= thresholds.get(i)[0]) {
                //System.out.println(thresholds.get(i)[0]);
                return i;
            }
        }

        return 0;
    }

    public void update() {
        if (cfg.GetGPUFanControlState().equals("0")) return;
        currentTemp = stats.GetCurrentTemp();
        UpdateFanSpeed();
    }

    public void quit() {
        Process p;
        try{
            String[] cmd = {"sh", fanCon, "1", "38"};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
