import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ValueExtract {
    private BufferedReader text;
    private ArrayList<String> lines;
    final private ArrayList<Integer> temps = new ArrayList<>();
    final private ArrayList<Integer> speeds = new ArrayList<>();
    final private int[] tempReturn;
    final private int[] speedReturn;
    final private String userName = System.getProperty("user.name");
    final private File input = new File("/home/" + userName + "/Documents/gmon", "gputemps.log"); // Change /valence to something from FileIO
    private Boolean isOpen = false;

    public ValueExtract() throws IOException {
        try { OpenFile(); }
        catch (Exception e) {System.out.println("File could not be opened. Perhaps no log is generated?");}

        temps.clear();
        LogToString();
        TokenizeStrings();

        tempReturn = new int[temps.size()];
        for (int i = 0; i < temps.size(); i++) { tempReturn[i] = temps.get(i); }

        speedReturn = new int[speeds.size()];
        for (int i = 0; i < speeds.size(); i++) { speedReturn[i] = speeds.get(i); }
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
    }

    private void TokenizeStrings() {
        String currentValue = "";

        if (lines.size() == 0) return;

        // Currently, even numbers are temps and odd are fan speeds. This pattern will change as more stats are tracked.
        for (int i = 0; i < lines.size(); i++) {
            currentValue = lines.get(i);
            int cV = 0;

            // in the off chance that somehow a line is logged incorrectly, I'd rather have a try catch to ease a headache.
            try {
                cV = Integer.parseInt(currentValue.substring(0, currentValue.length()-1));
            } catch (Exception e) {
                System.out.println("Error parsing log. Line #" + currentValue + " appears to have been logged incorrectly.");
            }

            // https://stackoverflow.com/a/7342273 bit checking method for even vs odd.
            if((i & 1) == 0) {
                temps.add(cV);
            }
            else {
                speeds.add(cV);
            }
        }

        /* The implementation for this has changed due to the regex implementation in the bash script.
        I'm leaving it here as a comment for posterity's sake. If ever I need to revert back to string tokenizers,
        I want some references to be able to fall back to.

        for (int i = 0; i < lines.size() && endOfLines == false; i++) {
            StringTokenizer st1 = new StringTokenizer(lines.get(i), "C");
        }*/
    }

    public int[] GetTemps() {
        return tempReturn;
    }

    public int[] GetSpeeds() {
        return speedReturn;
    }

}