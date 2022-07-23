import scripts.ExecScripts;
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
    private final GUI gui;
    private final ValueExtract ve;
    private final ExecScripts execScripts;
    final private String userName = System.getProperty("user.name");
    final private String gmonDir = "/home/" + userName + "/.local/bin/gmon";

    public Main(String title) {
        super(title);

        ve = new ValueExtract();
        execScripts = new ExecScripts();

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,300);
        gui = new GUI(ve);
        getContentPane().add(gui);
        this.setVisible(true);

        update();
    }

    private void ExecScript() {
        execScripts.LogTemp();
        execScripts.LogFSpeed();
    }

    public void update() {
        while (true) {
            try {
                ExecScript();
                gui.update();
            } catch (IOException e) { throw new RuntimeException(e); }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) { e.printStackTrace();}
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
