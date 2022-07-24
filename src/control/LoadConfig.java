package control;

import java.io.*;

public class LoadConfig {
    final private String userName = System.getProperty("user.name");
    final private String workingDir = "/home/" + userName + "/.local/bin/gmon_parser/";
    final private File config = new File(workingDir, "gmon.conf");

    private String gpuFanControlState;
    private String workingString;

    private BufferedReader cfgText;

    public LoadConfig() throws IOException {
        System.out.println("Checking if config exists.");
        if (config.isFile()) configExists();
        else NoConfig();
    }

    private void configExists() throws IOException {
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

        cfgText.close();
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
}
