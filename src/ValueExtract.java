import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ValueExtract {
    private BufferedReader text;
    private ArrayList<String> lines;
    final private ArrayList<Integer> values = new ArrayList<>();
    private int[] value;
    final private File input = new File("/home/valence/", "gputemps.txt");
    private Boolean isOpen = false;

    public ValueExtract(String FieldName) throws IOException {
        try { OpenFile(); }
        catch (Exception e) {System.out.println("File could not be opened");}

        values.clear();
        Stringify();

        if (FieldName.equals("Temps")) TempTokenize();
        else if (FieldName.equals("Fan Speed")) FanTokenize();

        value = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            value[i] = values.get(i);
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

    private void TempTokenize() {
        Boolean endOfLines = false;
        String temp = "";

        if (lines.size() == 0)
            endOfLines = true;

        for (int i = 0; i < lines.size(); i++) {
            if (endOfLines)
                return;

            StringTokenizer st = new StringTokenizer(lines.get(i), "C");

            if (st.countTokens() == 2) {
                while (st.hasMoreTokens()) {
                    TempDelimitDetected(st.nextToken());
                }
            }
        }
    }

    private void FanTokenize() {
        Boolean endOfLines = false;
        String temp = "";

        if (lines.size() == 0)
            endOfLines = true;

        for (int i = 0; i < lines.size(); i++) {
            if (endOfLines)
                return;

            StringTokenizer st = new StringTokenizer(lines.get(i), "%");

            if (st.countTokens() == 3) {
                while (st.hasMoreTokens()) {
                    FanDelimitDetected(st.nextToken());
                }
            }
        }
    }

    private void TempDelimitDetected(String token) {
        String line = token.substring(token.length() - 2);
        try {
            values.add(Integer.parseInt(line));

        } catch (Exception e) { }
    }

    private void FanDelimitDetected(String token) {
        if (token.charAt(0) == '|') {
            String line = token.substring(token.length() - 2);
            try {
                values.add(Integer.parseInt(line));
            } catch (Exception e) {}
        }
    }

    public int[] GetValues() {
        return value;
    }

}