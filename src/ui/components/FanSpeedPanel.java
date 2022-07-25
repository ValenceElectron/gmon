package ui.components;

import interfaces.IUpdatablePanel;
import stats.Statistics;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FanSpeedPanel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;

    private JLabel currentFSpeed;
    private JLabel additionalFSpeedStat;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private int peakFSpeed;
    private int avgFSpeed;

    public FanSpeedPanel(Statistics stats, Color bgColor, Color fgColor) {
        this.stats = stats;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel tempText = new JLabel("Fan Speed:");
        currentFSpeed = new JLabel("0");

        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentFSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);

        tempText.setForeground(fgColor);
        currentFSpeed.setForeground(fgColor);
        currentFSpeed.setBackground(bgColor);

        currentFSpeed.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        add(tempText);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(currentFSpeed);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(LocalTime peak) {
        peak();
        avg();
        currentFSpeed.setText(stats.GetCurrentFSpeed() + "%");
        currentFSpeed.setToolTipText("Peak: " + peakFSpeed + "%, Peaked at: " + peak.format(timeFormatter) + ", Average: " + avgFSpeed + "%");
    }

    @Override
    public void peak() {
        peakFSpeed = stats.GetPeakFSpeed();
    }

    public void avg() {
        avgFSpeed = stats.GetAverageFSpeeds();
    }
}
