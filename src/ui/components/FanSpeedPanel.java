package ui.components;

import interfaces.IUpdatablePanel;
import stats.ValueExtract;

import javax.swing.*;
import java.awt.*;

public class FanSpeedPanel extends JPanel implements IUpdatablePanel {
    private final ValueExtract ve;
    private JLabel currentFSpeed;
    private JLabel additionalFSpeedStat;

    private int peakFSpeed;
    private int avgFSpeed;

    public FanSpeedPanel(ValueExtract ve) {
        this.ve = ve;
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
        currentFSpeed.setText(ve.GetFSpeeds().get(ve.GetFSpeeds().size() - 1) + "%");
        additionalFSpeedStat.setText("Average: " + avgFSpeed + ", Peak: " + peakFSpeed);
    }

    @Override
    public void peak() {
        peakFSpeed = ve.GetFSpeedPeak();
    }

    public void avg() {
        int sum = 0;
        for (int i = 0; i < ve.GetFSpeeds().size(); i++) {
            sum += ve.GetFSpeeds().get(i);
        }

        avgFSpeed = sum/ve.GetFSpeeds().size();
    }
}
