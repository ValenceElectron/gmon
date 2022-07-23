package ui;

import stats.ValueExtract;
import ui.components.FanSpeedPanel;
import ui.components.TemperaturePanel;
import ui.components.TimePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JPanel {
    private final ValueExtract ve;
    private final TemperaturePanel temps;
    private final FanSpeedPanel fSpeeds;
    private final TimePanel times;

    public GUI(ValueExtract values) {
        setLayout(new GridLayout(3,1));
        this.ve = values;

        temps = new TemperaturePanel(ve);
        add(temps);
        fSpeeds = new FanSpeedPanel(ve);
        add(fSpeeds);
        times = new TimePanel(ve);
        add(times);
    }

    public void update() throws IOException {
        ve.update();
        temps.update();
        fSpeeds.update();
        times.update();
    }
}
