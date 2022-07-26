package ui.components;

import stats.Statistics;
import interfaces.IUpdatablePanel;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimePanel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;

    private JLabel elapsedTimeText;
    private JLabel elapsedTimeLabel;

    private ArrayList<LocalTime> times = new ArrayList<>();
    private long startTime;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private int peakIndex = 0;
    private String elapsedTime;

    public TimePanel(Statistics stats, Color bgColor, Color fgColor) {
        this.stats = stats;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        startTime = System.currentTimeMillis();

        elapsedTimeText = new JLabel("Time Elapsed:");
        elapsedTimeLabel = new JLabel("0");
        elapsedTimeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        elapsedTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        elapsedTimeText.setForeground(fgColor);
        elapsedTimeText.setBackground(bgColor);
        elapsedTimeLabel.setForeground(fgColor);
        elapsedTimeLabel.setBackground(bgColor);

        elapsedTimeLabel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        add(elapsedTimeText);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(elapsedTimeLabel);
    }

    @Override
    public void update() {
        peak();
        HowMuchTimeElapsed();
        times.add(LocalTime.now());
        elapsedTimeLabel.setText(elapsedTime);
    }

    // Currently unused. Byproduct of implementing IUpdatablePanel
    //
    @Override
    public void update(LocalTime peak) {

    }

    public LocalTime GetPeakTime() { return times.get(peakIndex); }

    @Override
    public void peak() {
        peakIndex = stats.GetPeakIndex();
    }

    private void HowMuchTimeElapsed() {
        long currentTimeElap = System.currentTimeMillis();
        long timeInMill = currentTimeElap - startTime;

        elapsedTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeInMill),
                TimeUnit.MILLISECONDS.toMinutes(timeInMill) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMill)),
                TimeUnit.MILLISECONDS.toSeconds(timeInMill) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMill)));
    }
}
