package ui.components;

import interfaces.IUpdatablePanel;
import stats.Statistics;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TemperaturePanel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;
    private JLabel currentTemp;
    private JLabel additionalTempStat;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private int peakTemp = 0;
    private int avgTemp = 0;

    public TemperaturePanel(Statistics stats, Color bgColor, Color fgColor) {
        this.stats = stats;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel tempText = new JLabel("Temperature:");
        currentTemp = new JLabel("0");

        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTemp.setAlignmentX((Component.CENTER_ALIGNMENT));

        tempText.setForeground(fgColor);
        currentTemp.setForeground(fgColor);
        currentTemp.setBackground(bgColor);

        currentTemp.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        add(tempText);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(currentTemp);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(LocalTime peak) {
        peak();
        avg();
        currentTemp.setText(stats.GetCurrentTemp() + "C");
        currentTemp.setToolTipText("Peak: " + peakTemp + "C, Peaked at: " + peak.format(timeFormatter) + ", Average: " + avgTemp + "C");
    }

    @Override
    public void peak() { peakTemp = stats.GetPeakTemp(); }

    public void avg() {
        avgTemp = stats.GetAverageTemps();
    }
}
