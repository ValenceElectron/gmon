package stats;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ValueExtract {
    private BufferedReader text;
    private BufferedReader tempText;
    private BufferedReader fSpeedText;

    private ArrayList<String> lines;
    private String tempLine;
    private String fSpeedLine;

    final private ArrayList<Integer> temps = new ArrayList<>();
    final private ArrayList<Integer> fSpeeds = new ArrayList<>();
    //final private ArrayList<int[]> times = new ArrayList<>();
    /*private int[] tempReturn;
    private int[] speedReturn;
    private int[][] timeReturn;*/

    //final private String userName = System.getProperty("user.name");
    final private String workingDir = System.getProperty("user.dir");
    final private File inputTemp = new File(workingDir+"/logs", "gputemps.log");
    final private File inputFSpeeds = new File(workingDir + "/logs", "gpufspeed.log");

    private Boolean isTempOpen = false;
    private Boolean isFSpeedOpen = false;

    public ValueExtract() {
        temps.clear();
        fSpeeds.clear();
        //times.clear();
    }


    private void OpenFiles() throws FileNotFoundException {
        OpenTemps();
        OpenFSpeeds();
    }

    private void OpenTemps() throws FileNotFoundException {
        if (isTempOpen)
            return;

        tempText = new BufferedReader((new FileReader(inputTemp)));
        isTempOpen = true;
    }

    private void OpenFSpeeds() throws FileNotFoundException {
        if (isFSpeedOpen)
            return;

        fSpeedText = new BufferedReader((new FileReader(inputFSpeeds)));
        isFSpeedOpen = true;
    }

    // TODO: remove this entirely.
    private void LogsToStrings() throws IOException {
        TempLogToString();
        FSpeedLogToString();
    }

    private void TempLogToString() throws IOException {
        tempLine = tempText.readLine();
        tempText.close();
        isTempOpen = false;
    }

    private void FSpeedLogToString() throws IOException {
        fSpeedLine = fSpeedText.readLine();
        fSpeedText.close();
        isFSpeedOpen = false;
    }

    // TODO: Remove this entirely. The refactoring should take care of everything in here.
    private void FormatStrings() {
        FormatTemps();
        FormatFSpeeds();
    }

    private void FormatTemps() {
        int currentValue = Integer.parseInt(tempLine);
        temps.add(currentValue);
    }

    private void FormatFSpeeds() {
        int currentValue = Integer.parseInt(fSpeedLine.substring(0, fSpeedLine.length()-2));
        fSpeeds.add(currentValue);
    }

    // This is needed to break down the time string into its hour, minute, and second components.
    private int[] ParseTime(String time) {
        int[] ret = new int[3];

        StringTokenizer st = new StringTokenizer(time, ":");
        ret[0] = Integer.parseInt(st.nextToken());
        ret[1] = Integer.parseInt(st.nextToken());
        ret[2] = Integer.parseInt(st.nextToken());

        return ret;
    }

    public ArrayList<Integer> GetTemps() { return temps; }

    public ArrayList<Integer> GetFSpeeds() { return fSpeeds; }

    /*public int[][] GetTimes() {
        return timeReturn;
    }*/

    // TODO: refactor so we no longer need int[]'s. Do any converting from ArrayList to Array in specific methods, not in update().
    public void update() throws IOException {
        try { OpenFiles(); }
        catch (Exception e) { System.out.println("File could not be opened. Perhaps no log is generated?"); }

        LogsToStrings();
        FormatStrings();
    }

    /*public void ClearHistory() {
        temps.clear();
        fSpeeds.clear();
        times.clear();
        tempReturn = new int[0];
        speedReturn = new int[0];
        timeReturn = new int[0][];
    }*/
}