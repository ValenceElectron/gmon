package ui.components;

import interfaces.IUpdatablePanel;
import stats.ValueExtract;

import javax.swing.*;
import java.awt.*;

public class TemperaturePanel extends JPanel implements IUpdatablePanel {
    private final ValueExtract ve;
    private JLabel currentTemp;
    private JLabel additionalTempStat;

    private int peakTemp = 0;
    private int avgTemp = 0;

    public TemperaturePanel(ValueExtract ve) {
        this.ve = ve;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel tempText = new JLabel("Temperature");
        currentTemp = new JLabel("0");
        additionalTempStat = new JLabel("Average: " + avgTemp + ", Peak: " + peakTemp);
        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTemp.setAlignmentX((Component.CENTER_ALIGNMENT));
        additionalTempStat.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(tempText);
        add(currentTemp);
        add(additionalTempStat);
    }

    @Override
    public void update() {
        peak();
        avg();
        currentTemp.setText(ve.GetTemps().get(ve.GetTemps().size() - 1) + "C");
        additionalTempStat.setText("Average: " + avgTemp + ", Peak: " + peakTemp);
    }

    @Override
    public void peak() {
        peakTemp = ve.GetTempPeak();
    }

    public void avg() {
        int sum = 0;
        for (int i = 0; i < ve.GetTemps().size(); i++) {
            sum += ve.GetTemps().get(i);
        }

        avgTemp = sum/ve.GetTemps().size();
    }
}
