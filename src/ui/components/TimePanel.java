package ui.components;

import interfaces.IUpdatablePanel;
import stats.ValueExtract;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimePanel extends JPanel implements IUpdatablePanel {
    private final ValueExtract ve;
    private JLabel currentTime;
    private ArrayList<LocalTime> times = new ArrayList<>();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public TimePanel(ValueExtract ve) {
        this.ve = ve;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel timeText = new JLabel("Logged at: ");
        currentTime = new JLabel("0");
        timeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTime.setAlignmentX((Component.CENTER_ALIGNMENT));

        add(timeText);
        add(currentTime);
    }

    @Override
    public void update() {
        times.add(LocalTime.now());
        currentTime.setText(times.get(times.size() -1).format(timeFormatter));
    }
}
