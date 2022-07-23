package ui;

import stats.datastructs.Statistics;

public class FormatDisplay {
    final private Statistics stats;
    private int lineLength;
    private String dashLine;

    public FormatDisplay(int[] dataset, String name) {
        stats = new Statistics(dataset, name);
        FormatTable();
        PerformStats();
    }

    private void FormatTable() {
        String bigLine = "Sample Size: " + stats.NumberOfValues();
        lineLength = bigLine.length();
        dashLine = "+";
        for (int i = 0; i < lineLength + 2; i++) {
            dashLine += "-";
        }
        dashLine += "+";
    }

    private void PerformStats() {
        PEmptyLine();
        p(stats.GetFieldName());
        PEmptyLine();
        p("Min: " + stats.Minimum());
        p("Max: " + stats.Maximum());
        p("Avg: " + stats.Average());
        p("Sample Size: " + stats.NumberOfValues());
        PEmptyLine();
    }

    private void p(String s) {
        String spacer = "";
        for (int i = 0; i <= lineLength - s.length(); i++) {
            spacer += " ";
        }
        System.out.println("| " + s + spacer + "|");
    }

    private void PEmptyLine() {
        System.out.println(dashLine);
    }
}
