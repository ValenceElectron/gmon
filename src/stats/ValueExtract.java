package stats;

import java.io.*;
import java.util.ArrayList;

public class ValueExtract {
    private BufferedReader tempText;
    private BufferedReader fSpeedText;

    private String tempLine;
    private String fSpeedLine;

    private int peakTemp = 0;
    private int peakFSpeed = 0;
    private int peakIndex = 0;

    final private ArrayList<Integer> temps = new ArrayList<>();
    final private ArrayList<Integer> fSpeeds = new ArrayList<>();
    /*private int[] tempReturn;
    private int[] speedReturn;*/

    //final private String userName = System.getProperty("user.name");
    final private String workingDir = System.getProperty("user.dir");
    final private File inputTemp = new File(workingDir+"/logs", "gputemps.log");
    final private File inputFSpeeds = new File(workingDir + "/logs", "gpufspeed.log");

    private Boolean isTempOpen = false;
    private Boolean isFSpeedOpen = false;

    public ValueExtract() {
        temps.clear();
        fSpeeds.clear();
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

    private void FindPeaks() {
        if(peakTemp <= temps.get(temps.size() - 1)) {
            peakTemp = temps.get(temps.size() - 1);
            peakIndex = temps.size() - 1;
        }

        if(peakFSpeed <= fSpeeds.get(fSpeeds.size() - 1)) peakFSpeed = fSpeeds.get(fSpeeds.size() - 1);
    }

    public ArrayList<Integer> GetTemps() { return temps; }

    public int GetTempPeak() { return peakTemp; }

    public ArrayList<Integer> GetFSpeeds() { return fSpeeds; }

    public int GetFSpeedPeak() { return peakFSpeed; }

    public int GetPeakIndex() { return peakIndex; }

    public void update() throws IOException {
        try { OpenFiles(); }
        catch (Exception e) { System.out.println("File could not be opened. Perhaps no log is generated?"); }
        LogsToStrings();
        FormatStrings();
        FindPeaks();
    }

    /*public void ClearHistory() {
        temps.clear();
        fSpeeds.clear();
        tempReturn = new int[0];
        speedReturn = new int[0];
    }*/
}