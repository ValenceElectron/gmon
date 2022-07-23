package ui.components;

import interfaces.IUpdatablePanel;
import stats.ValueExtract;

import javax.swing.*;
import java.awt.*;

public class Temperature extends JPanel implements IUpdatablePanel {
    private final ValueExtract ve;
    private JLabel currentTemp;

    public Temperature(ValueExtract ve) {
        this.ve = ve;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel tempText = new JLabel("Temperature");
        currentTemp = new JLabel("0");
        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTemp.setAlignmentX((Component.CENTER_ALIGNMENT));

        add(tempText);
        add(currentTemp);
    }

    @Override
    public void update() {
        int[] temps = ve.GetTemps();
        currentTemp.setText(Integer.toString(temps[temps.length - 1]) + "C");
    }
}
