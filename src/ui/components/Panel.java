package ui.components;

import interfaces.IUpdatablePanel;
import stats.Statistics;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Panel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;
    private final String parameter;
    private final String initValue;
    private JLabel valueText;
    private JLabel currentValue;

    private int peakValue = 0;
    private int avgValue = 0;

    private Color[] colors = new Color[4];

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Panel(Statistics stats, Color[] colors, String parameter, String initValue) {
        this.stats = stats; this.parameter = parameter; this.initValue = initValue;
        this.colors = new Color[]{ colors[0], colors[1], colors[2], colors[3] };


    }

    //------------------------------------------------------------------------------------------------------------------

    private void init() {
        valueText = new JLabel(parameter + ":");
        currentValue = new JLabel(initValue);

        valueText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueText.setForeground(colors[1]);
        currentValue.setForeground(colors[1]);
        currentValue.setBackground(colors[0]);

        currentValue.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        add(valueText);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(currentValue);
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void peak() {
        peakValue = stats.GetPeak(parameter);
    }

    public void avg() {
        avgValue = stats.GetAverage(parameter);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(LocalTime peak) {
        peak();
        avg();
        currentValue.setText(Integer.toString(stats.GetCurrent(parameter)));
        currentValue.setToolTipText("Peak: " + peakValue + "%, Peaked at: " + peak.format(timeFormatter) +
                ", Average: " + avgValue + "%");
    }


}
