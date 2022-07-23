import stats.ValueExtract;
import ui.FormatDisplay;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Timer;

public class Main extends JFrame {
    private GUI gui;
    private ValueExtract ve;

    public Main(String title) throws IOException {
        super(title);

        ve = new ValueExtract();

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        gui = new GUI(ve);
        getContentPane().add(gui);
        this.setVisible(true);
    }

    public void update() {
        /*Thread updateThread = new Thread() {
            public void run() {
                while (true) {
                    ValueExtract ExtractStats = null;
                    try {
                        ExtractStats = new ValueExtract();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int[] temps = ExtractStats.GetTemps(); Arrays.sort(temps);
                    int[] fans = ExtractStats.GetSpeeds(); Arrays.sort(fans);
                    //int[][] times = ExtractStats.GetTimes();
                    FormatDisplay displayTempStats = new FormatDisplay(temps, "Temps (Celsius)");
                    FormatDisplay displayFanStats = new FormatDisplay(fans, "Fan Speed (%)");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) { e.printStackTrace();}
                }
            }
        };*/
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
