package ui;

import stats.ValueExtract;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JPanel {
    private ValueExtract ve;
    private JLabel currentTemp;

    public GUI(ValueExtract values) {
        setLayout(new GridLayout(3,1));

        this.ve = values;

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel tempText = new JLabel("Temperature");
        currentTemp = new JLabel("0");
        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTemp.setAlignmentX((Component.CENTER_ALIGNMENT));

        panel1.add(tempText);
        panel1.add(currentTemp);
        add(panel1);

        //add(new JLabel("Fan Speed"));
        //add(new JLabel("Last Update"));
    }

    public void update() throws IOException {
        ve.update();
        int[] temps = ve.GetTemps();
        currentTemp.setText(Integer.toString(temps[temps.length - 1]));
    }
}
