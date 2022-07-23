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
    final private String userName = System.getProperty("user.name");
    final private String gmonDir = "/home/" + userName + "/.local/bin/gmon";

    public Main(String title) throws IOException {
        super(title);

        ve = new ValueExtract();

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,300);
        gui = new GUI(ve);
        getContentPane().add(gui);
        this.setVisible(true);
        update();
    }

    private void ExecScript() {
        Process p;
        try{
            String[] cmd = {"sh", gmonDir};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
