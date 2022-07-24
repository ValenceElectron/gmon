package ui.components;

import interfaces.IUpdatablePanel;
import stats.ValueExtract;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimePanel extends JPanel implements IUpdatablePanel {
    private final ValueExtract ve;

    private JLabel currentTime;
    private JLabel peakTime;
    private JLabel elapsedTimeLabel;

    private ArrayList<LocalTime> times = new ArrayList<>();
    private long startTime;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private int peakIndex = 0;
    private String elapsedTime;

    public TimePanel(ValueExtract ve) {
        this.ve = ve;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        startTime = System.currentTimeMillis();

        //JLabel timeText = new JLabel("Logged at: ");
        //currentTime = new JLabel("0");
        peakTime = new JLabel("Peaked at: " + 0);
        elapsedTimeLabel = new JLabel("Time elapsed: " + 0);
        //timeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        //currentTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        peakTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        elapsedTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add(timeText);
        //add(currentTime);
        add(peakTime);
        add(elapsedTimeLabel);
    }

    @Override
    public void update() {
        peak();
        HowMuchTimeElapsed();
        times.add(LocalTime.now());
        //currentTime.setText(times.get(times.size() -1).format(timeFormatter));
        peakTime.setText("Peaked at: " + times.get(peakIndex).format(timeFormatter));
        elapsedTimeLabel.setText("Time elapsed: " + elapsedTime);
    }

    @Override
    public void peak() {
        peakIndex = ve.GetPeakIndex();
    }

    private void HowMuchTimeElapsed() {
        long currentTimeElap = System.currentTimeMillis();
        long timeInMill = currentTimeElap - startTime;

        elapsedTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeInMill),
                TimeUnit.MILLISECONDS.toMinutes(timeInMill) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMill)),
                TimeUnit.MILLISECONDS.toSeconds(timeInMill) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMill)));
    }
}