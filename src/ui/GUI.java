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

    private final Color[] colors = new Color[4];

    // TODO: Menu bar with options to create a custom fan curve + JFrame and JPanels for it.
    // TODO: Add more colors set in main (maybe an array?) to be able to pass to the xPanels. Especially red for critical temps.
    // TODO: Refactor components to be more object oriented.
    //
    public GUI(Statistics stats, Color[] colors) {
        setLayout(new GridLayout(3,1));
        this.stats = stats;
        this.colors[0] = colors[0]; this.colors[1] = colors[1]; this.colors[2] = colors[2]; this.colors[3] = colors[3];

        InitTrackingPanels();
    }


    //------------------------------------------------------------------------------------------------------------------
    // init methods.


    private void InitTrackingPanels() {
        System.out.println("Initializing monitoring panels.");
        temps = new TemperaturePanel(stats, colors);
        fSpeeds = new FanSpeedPanel(stats, colors);
        times = new TimePanel(stats, colors);

        add(temps);
        add(fSpeeds);
        add(times);
    }


    //------------------------------------------------------------------------------------------------------------------
    // update methods.


    public void update() throws IOException {
        times.update();
        fSpeeds.update(times.GetPeakTime());
        temps.update(times.GetPeakTime());
    }
}
