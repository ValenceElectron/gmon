import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TempExtract {
    private BufferedReader text;
    private ArrayList<String> lines;
    final private ArrayList<Integer> temps = new ArrayList<>();
    private int[] temp;
    final private File input = new File("/home/valence/", "gputemps.txt");
    private Boolean isOpen = false;

    public TempExtract() {

    }

    public void ExtractTemps() throws IOException {
        try { OpenFile(); }
        catch (Exception e) {System.out.println("File could not be opened");}

        temps.clear();

        Stringify();
        Tokenize();

        temp = new int[temps.size()];
        for (int i = 0; i < temps.size(); i++) {
            temp[i] = temps.get(i);
        }
    }

    private void OpenFile() throws FileNotFoundException {
        if (isOpen)
            return;

        text = new BufferedReader((new FileReader(input)));

        isOpen = true;
    }

    private void Stringify() throws IOException {
        lines = new ArrayList<>();
        Boolean endOfFile = false;
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

    private void Tokenize() {
        Boolean endOfLines = false;
        String temp = "";

        if (lines.size() == 0)
            endOfLines = true;

        for (int i = 0; i < lines.size(); i++) {
            if (endOfLines)
                return;

            StringTokenizer st = new StringTokenizer(lines.get(i), "C");
            //System.out.println(Integer.toString(st.countTokens()));
            if (st.countTokens() == 2) {
                while (st.hasMoreTokens()) {
                    DelimitDetected(st.nextToken());
                }
            }
        }
    }

    private void DelimitDetected(String token) {
        String line = token.substring(token.length() - 2);
        try {
            temps.add(Integer.parseInt(line));

        } catch (Exception e) { }
    }

    public int[] GetTemps() {
        return temp;
    }

}
