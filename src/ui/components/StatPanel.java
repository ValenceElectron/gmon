package ui.components;

import interfaces.IUpdatablePanel;
import stats.Statistics;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StatPanel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;
    private final String parameter;
    private final String suffix;
    private final JLabel valueText;
    private final JLabel currentValue;

    private int peakValue = 0;
    private int avgValue = 0;

    private long startTime = 0;
    private final ArrayList<LocalTime> times = new ArrayList<>();
    private String elapsedTime;

    private final Color[] colors = new Color[4];

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public StatPanel(Statistics stats, Color[] colors, String parameter, String initValue) {
        this.stats = stats; this.parameter = parameter;
        this.colors[0] = colors[0]; this.colors[1] = colors[1]; this.colors[2] = colors[2]; this.colors[3] = colors[3];
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(this.colors[0]);
        setForeground(this.colors[1]);

        valueText = new JLabel(parameter + ":");
        currentValue = new JLabel(initValue);

        if (parameter.equals("Time Elapsed")) {
            startTime = System.currentTimeMillis();
            suffix = "";
        } else if (parameter.equals("Fan Speed"))
            suffix = "%";
        else
            suffix = "C";
        init();
    }

    //------------------------------------------------------------------------------------------------------------------

    private void init() {
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

    private void HowMuchTimeElapsed() {
        long currentTimeElapsed = System.currentTimeMillis();
        long timeInMill = currentTimeElapsed - startTime;

        elapsedTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeInMill),
                TimeUnit.MILLISECONDS.toMinutes(timeInMill) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMill)),
                TimeUnit.MILLISECONDS.toSeconds(timeInMill) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMill)));
    }

    public LocalTime GetPeakTime() { return times.get(peakValue); }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void update() {
        peak();
        HowMuchTimeElapsed();
        times.add(LocalTime.now());
        currentValue.setText(elapsedTime);
    }

    @Override
    public void update(LocalTime peak) {
        peak();
        avg();
        currentValue.setText(parameter + suffix);
        currentValue.setToolTipText("Peak: " + peakValue + suffix + ", Peaked at: " + peak.format(timeFormatter) +
                ", Average: " + avgValue + suffix);
    }


}
