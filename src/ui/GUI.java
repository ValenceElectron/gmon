package ui;

import stats.ValueExtract;
import ui.components.FanSpeed;
import ui.components.Temperature;
import ui.components.TimeWidget;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JPanel {
    private final ValueExtract ve;
    private final Temperature temps;
    private final FanSpeed speeds;
    private final TimeWidget times;

    public GUI(ValueExtract values) {
        setLayout(new GridLayout(3,1));
        this.ve = values;

        temps = new Temperature(ve);
        add(temps);
        speeds = new FanSpeed(ve);
        add(speeds);
        times = new TimeWidget(ve);
        add(times);

        //add(new JLabel("Fan Speed"));
        //add(new JLabel("Last Update"));
    }

    public void update() throws IOException {
        ve.update();
        temps.update();
        speeds.update();
        times.update();
    }
}
