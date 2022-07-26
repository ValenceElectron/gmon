package control;

import java.io.*;
import java.util.ArrayList;

public class LoadConfig {
    final private String userName = System.getProperty("user.name");
    final private String workingDir = "/home/" + userName + "/.local/bin/gmon_parser/";
    final private File config = new File(workingDir, "gmon.conf");

    private String gpuFanControlState;
    private ArrayList<String> thresholds;
    private ArrayList<Integer> tempThresh;
    private ArrayList<Integer> fSpeedThresh;

    private BufferedReader cfgText;

    public LoadConfig() throws IOException {
        System.out.println("Checking if config exists.");
        if (config.isFile()) configExists();
        else NoConfig();
    }

    // TODO: Make sure config file specifies at least 2 points on fan curve, if not, load default fan curve.
    // TODO: Make a cap for amount of points on fan curve. Ideally, 8-10 max, then disregard any lines in gmon.conf after.
    // TODO: Validate that each line starts with GPUFanP[number], and that they're in order.
    // TODO: Validate that each value pulled from lines with GPUFanP[number] are indeed numbers.
    private void configExists() throws IOException {
        String workingString;

        System.out.println("Config exists.");
        cfgText = new BufferedReader(new FileReader(config));
        workingString = cfgText.readLine();

        System.out.println(workingString);
        if (workingString.equals("GPUFanControlState=0")) {
            gpuFanControlState = "0";
            cfgText.close();
            return;
        } else if (workingString.equals("GPUFanControlState=1")) {
            gpuFanControlState = "1";
        } else {
            System.out.println("Error reading config. Making new default config.");
            config.delete();
            NoConfig();
            return;
        }

        thresholds = new ArrayList<>();
        workingString = "";
        boolean endOfFile = false;

        while (!endOfFile) {
            workingString = cfgText.readLine();
            if (workingString == null) endOfFile = true;
            else thresholds.add(workingString);
        }

        cfgText.close();

        ParseThresholds();
    }

    private void ParseThresholds() {

        if (thresholds.size() == 0) {
            System.out.println("No thresholds in config. Input thresholds using 'GPUFanOne,30,40'");
            System.out.println("Where 30 is the tempereature in Celsius, and 40 is percentage of fan max speed.");
            return;
        }

        tempThresh = new ArrayList<>(); fSpeedThresh = new ArrayList<>();

        for (int i = 0; i < thresholds.size(); i++) {
            String[] strArr = thresholds.get(i).split(",", 3);
            //System.out.println(strArr[1] + "," + strArr[2]);

            tempThresh.add(Integer.parseInt(strArr[1])); fSpeedThresh.add(Integer.parseInt(strArr[2]));
        }
    }

    private void NoConfig() throws IOException {
        System.out.println("Config doesn't exist, creating config at ~/.local/bin/gmon_parser/gmon.conf");
        config.createNewFile();
        PrintWriter print = new PrintWriter(new FileWriter(config, false));
        print.print("GPUFanControlState=0");
        print.close();

        gpuFanControlState = "0";
        System.out.println("Set GPUFanControlState=0");
    }

    public String GetGPUFanControlState() {
        return gpuFanControlState;
    }

    public ArrayList<Integer> GetTempThresholds() { return tempThresh; }
    public ArrayList<Integer> GetFSpeedThresholds() { return fSpeedThresh; }
}
