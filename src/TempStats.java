public class TempStats {
    final private int[] temps;

    public TempStats(int[] temps) {
        this.temps = temps;
        PerformStats();
    }

    private void PerformStats() {
        p("Min: " + temps[0]);
        p("Max: " + temps[temps.length - 1]);

        int average = 0;
        int avg = 0;

        for (int i = 0; i < temps.length; i++) {
            average += temps[i];
            avg = average / temps.length;
        }

        p("Avg: " + avg);
        p("Measures: " + temps.length);
    }

    private void p(String s) {
        System.out.println(s);
    }
}
