package ui.components;

import interfaces.IUpdatablePanel;
import stats.Statistics;
import javax.swing.*;
import java.awt.*;

public class FanSpeedPanel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;

    private JLabel currentFSpeed;
    private JLabel additionalFSpeedStat;

    private int peakFSpeed;
    private int avgFSpeed;

    public FanSpeedPanel(Statistics stats) {
        this.stats = stats;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel tempText = new JLabel("Fan Speed");
        currentFSpeed = new JLabel("0");
        additionalFSpeedStat = new JLabel("Average: " + avgFSpeed + ", Peak: " + peakFSpeed);
        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentFSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);
        additionalFSpeedStat.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(tempText);
        add(currentFSpeed);
        add(additionalFSpeedStat);
    }

    @Override
    public void update() {
        peak();
        avg();
        currentFSpeed.setText(stats.GetCurrentFSpeed() + "%");
        additionalFSpeedStat.setText("Average: " + avgFSpeed + ", Peak: " + peakFSpeed);
    }

    @Override
    public void peak() {
        peakFSpeed = stats.GetPeakFSpeed();
    }

    public void avg() {
        avgFSpeed = stats.GetAverageFSpeeds();
    }
}
