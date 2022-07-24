package ui.components;

import interfaces.IUpdatablePanel;
import stats.Statistics;
import javax.swing.*;
import java.awt.*;

public class TemperaturePanel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;
    private JLabel currentTemp;
    private JLabel additionalTempStat;

    private int peakTemp = 0;
    private int avgTemp = 0;

    public TemperaturePanel(Statistics stats) {
        this.stats = stats;
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
        currentTemp.setText(stats.GetCurrentTemp() + "C");
        additionalTempStat.setText("Average: " + avgTemp + ", Peak: " + peakTemp);
    }

    @Override
    public void peak() { peakTemp = stats.GetPeakTemp(); }

    public void avg() {
        avgTemp = stats.GetAverageTemps();
    }
}
