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

    public GUI(Statistics stats) {
        setLayout(new GridLayout(3,1));
        this.stats = stats;

        temps = new TemperaturePanel(stats);
        add(temps);
        fSpeeds = new FanSpeedPanel(stats);
        add(fSpeeds);
        times = new TimePanel(stats);
        add(times);
    }

    public void update() throws IOException {
        temps.update();
        fSpeeds.update();
        times.update();
    }
}
