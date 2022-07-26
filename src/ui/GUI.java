/*
 *******************************************************************************
 * Copyright (c) ValenceElectron (Github: ValenceElectron,                     *
 * Twitter: TheLastElectron, Email: TheLastElectronSPDF@gmail.com)             *
 *-----------------------------------------------------------------------------*
 * Program that monitors various aspects of your GPU and displays them in a    *
 * window. Also allows for settings custom fan curves.                         *
 *-----------------------------------------------------------------------------*
 * This file is part of the gmon_parser project.                               *
 *******************************************************************************
/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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
    private TemperaturePanel temps;
    private FanSpeedPanel fSpeeds;
    private TimePanel times;
    private final Color bgColor;
    private final Color fgColor;

    // TODO: Menu bar with options to create a custom fan curve + JFrame and JPanels for it.
    // TODO: Decouple any of the data processing from the xPanel classes so that it can run independent of the GUI.
    // TODO: Add more colors set in main (maybe an array?) to be able to pass to the xPanels. Especially red for critical temps.
    //
    public GUI(Statistics stats, Color bgColor, Color fgColor) {
        setLayout(new GridLayout(3,1));
        this.stats = stats;
        this.bgColor = bgColor;
        this.fgColor = fgColor;

        InitTrackingPanels();
    }

    private void InitTrackingPanels() {
        System.out.println("Initializing monitoring panels.");
        temps = new TemperaturePanel(stats, bgColor, fgColor);
        fSpeeds = new FanSpeedPanel(stats, bgColor, fgColor);
        times = new TimePanel(stats, bgColor, fgColor);

        temps.setBackground(bgColor);
        temps.setForeground(fgColor);
        add(temps);

        fSpeeds.setBackground(bgColor);
        fSpeeds.setForeground(fgColor);
        add(fSpeeds);

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
