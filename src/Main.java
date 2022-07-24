import control.FanControl;
import scripts.ExecScripts;
import stats.Statistics;
import stats.ValueExtract;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    private final GUI gui;
    private final ValueExtract ve;
    private final Statistics stats;
    private final ExecScripts execScripts;
    private final FanControl fCon;
    final private String userName = System.getProperty("user.name");

    public Main(String title) {
        super(title);

        stats = new Statistics();
        ve = new ValueExtract(stats);
        execScripts = new ExecScripts();
        fCon = new FanControl(stats);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,300);
        gui = new GUI(stats);
        getContentPane().add(gui);
        this.setVisible(true);

        update();
    }

    private void ExecScript() {
        execScripts.LogTemp();
        execScripts.LogFSpeed();
    }

    private void ExecControl() {

    }

    public void update() {
        while (true) {
            try {
                ExecScript();
                ve.update();
                gui.update();
            } catch (IOException e) { throw new RuntimeException(e); }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace();}
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
