package ui.components;

import interfaces.IUpdatablePanel;
import stats.ValueExtract;

import javax.swing.*;
import java.awt.*;

public class FanSpeed extends JPanel implements IUpdatablePanel {
    private final ValueExtract ve;
    private JLabel currentSpeed;

    public FanSpeed(ValueExtract ve) {
        this.ve = ve;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel tempText = new JLabel("Fan Speed");
        currentSpeed = new JLabel("0");
        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentSpeed.setAlignmentX((Component.CENTER_ALIGNMENT));

        add(tempText);
        add(currentSpeed);
    }

    @Override
    public void update() {
        currentSpeed.setText(ve.GetFSpeeds().get(ve.GetFSpeeds().size() - 1) + "%");
    }
}
