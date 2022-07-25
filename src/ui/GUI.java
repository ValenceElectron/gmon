package ui;

import stats.Statistics;
import stats.ValueExtract;
import ui.components.FanSpeedPanel;
import ui.components.TemperaturePanel;
import ui.components.TimePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JPanel {
    private final Statistics stats;
    private final TemperaturePanel temps;
    private final FanSpeedPanel fSpeeds;
    private final TimePanel times;

    public GUI(Statistics stats, Color bgColor, Color fgColor) {
        setLayout(new GridLayout(3,1));
        this.stats = stats;

        temps = new TemperaturePanel(stats, bgColor, fgColor);
        temps.setBackground(bgColor);
        temps.setForeground(fgColor);
        add(temps);

        fSpeeds = new FanSpeedPanel(stats, bgColor, fgColor);
        fSpeeds.setBackground(bgColor);
        fSpeeds.setForeground(fgColor);
        add(fSpeeds);

        times = new TimePanel(stats, bgColor, fgColor);
        times.setBackground(bgColor);
        times.setForeground(fgColor);
        add(times);
    }

    public void update() throws IOException {
        times.update();
        fSpeeds.update(times.GetPeakTime());
        temps.update(times.GetPeakTime());
    }
}
