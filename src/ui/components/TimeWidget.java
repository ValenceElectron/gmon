package ui.components;

import interfaces.IUpdatablePanel;
import stats.ValueExtract;

import javax.swing.*;
import java.awt.*;

public class TimeWidget extends JPanel implements IUpdatablePanel {
    private final ValueExtract ve;
    private JLabel currentTime;

    public TimeWidget(ValueExtract ve) {
        this.ve = ve;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel timeText = new JLabel("Logged at: ");
        currentTime = new JLabel("0");
        timeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTime.setAlignmentX((Component.CENTER_ALIGNMENT));

        add(timeText);
        add(currentTime);
    }

    private void FormatTime(int[] time) {
        String theTime[] = new String[3];
        theTime[0] = Integer.toString(time[0]);
        theTime[1] = Integer.toString(time[1]);
        theTime[2] = Integer.toString(time[2]);

        for (int i = 0; i < theTime.length; i++) {
            if (theTime[i].length() < 2)
                theTime[i] = "0" + theTime[i];
        }

        currentTime.setText(theTime[0] + ":" + theTime[1] + ":" + theTime[2]);
    }

    @Override
    public void update() {
        int[][] times = ve.GetTimes();
        FormatTime(times[times.length-1]);
    }
}
