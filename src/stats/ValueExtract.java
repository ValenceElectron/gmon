package stats;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ValueExtract {
    private BufferedReader text;
    private ArrayList<String> lines;
    final private ArrayList<Integer> temps = new ArrayList<>();
    final private ArrayList<Integer> speeds = new ArrayList<>();
    final private ArrayList<int[]> times = new ArrayList<>();
    private int[] tempReturn;
    private int[] speedReturn;
    private int[][] timeReturn;
    final private String userName = System.getProperty("user.name");
    final private File input = new File("/home/" + userName + "/Documents/gmon", "gputemps.log"); // Change /valence to something from FileIO
    private Boolean isOpen = false;

    public ValueExtract() throws IOException {
        try { OpenFile(); }
        catch (Exception e) {System.out.println("File could not be opened. Perhaps no log is generated?");}

        temps.clear();
        speeds.clear();
        times.clear();
        LogToString();
        FormatStrings();

        tempReturn = new int[temps.size()];
        for (int i = 0; i < temps.size(); i++) { tempReturn[i] = temps.get(i); }

        speedReturn = new int[speeds.size()];
        for (int i = 0; i < speeds.size(); i++) { speedReturn[i] = speeds.get(i); }

        timeReturn = new int[times.size()][];
        for (int i = 0; i < times.size(); i++) { timeReturn[i] = times.get(i); }
    }

    private void OpenFile() throws FileNotFoundException {
        if (isOpen)
            return;

        text = new BufferedReader((new FileReader(input)));
        isOpen = true;
    }

    private void LogToString() throws IOException {
        lines = new ArrayList<>();
        boolean endOfFile = false;
        String line = "";

        while (!endOfFile) {
            line = text.readLine();
            if (line == null)
                endOfFile = true;
            else {
                lines.add(line);
            }
        }

        text.close();
        isOpen = false;
    }

    private void FormatStrings() {
        String currentValue = "";
        int query = 0;

        if (lines.size() == 0 || lines == null) return;

        // No need for if statements since the log file is generated with a set, sequential pattern.
        currentValue = lines.get(0);
        int cV = 0;

        cV = Integer.parseInt(currentValue.substring(0, currentValue.length()-1));
        temps.add(cV);

        currentValue = lines.get(1);
        cV = Integer.parseInt(currentValue.substring(0, currentValue.length()-1));
        speeds.add(cV);

        currentValue = lines.get(2);
        currentValue = currentValue.trim();
        times.add(ParseTime(currentValue));
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

    public int[] GetTemps() { return tempReturn; }

    public int[] GetSpeeds() { return speedReturn; }

    public int[][] GetTimes() {
        return timeReturn;
    }

    public void update() throws IOException {
        try { OpenFile(); }
        catch (Exception e) {System.out.println("File could not be opened. Perhaps no log is generated?");}

        LogToString();
        FormatStrings();

        tempReturn = new int[temps.size()];
        for (int i = 0; i < temps.size(); i++) { tempReturn[i] = temps.get(i); }

        speedReturn = new int[speeds.size()];
        for (int i = 0; i < speeds.size(); i++) { speedReturn[i] = speeds.get(i); }

        timeReturn = new int[times.size()][];
        for (int i = 0; i < times.size(); i++) { timeReturn[i] = times.get(i); }
    }

    public void ClearHistory() {
        temps.clear();
        speeds.clear();
        times.clear();
        tempReturn = new int[0];
        speedReturn = new int[0];
        timeReturn = new int[0][];
    }
}