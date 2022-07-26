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

package ui.components;

import interfaces.IUpdatablePanel;
import stats.Statistics;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TemperaturePanel extends JPanel implements IUpdatablePanel {
    private final Statistics stats;
    private JLabel currentTemp;
    private JLabel tempText;

    private int peakTemp = 0;
    private int avgTemp = 0;
    private final Color bgColor;
    private final Color fgColor;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public TemperaturePanel(Statistics stats, Color bgColor, Color fgColor) {
        this.stats = stats; this.bgColor = bgColor; this.fgColor = fgColor;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(bgColor);
        setForeground(fgColor);

        init();

        add(tempText);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(currentTemp);
    }

    // Function overloading required because this class implements IUpdatablePanel.
    //
    public void init() {
        tempText = new JLabel("Temperature:");
        currentTemp = new JLabel("0");

        tempText.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTemp.setAlignmentX((Component.CENTER_ALIGNMENT));

        tempText.setForeground(fgColor);
        currentTemp.setForeground(fgColor);
        currentTemp.setBackground(bgColor);

        currentTemp.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
    }

    @Override
    public void update() {

    }

    @Override
    public void update(LocalTime peak) {
        peak();
        avg();
        currentTemp.setText(stats.GetCurrentTemp() + "C");
        currentTemp.setToolTipText("Peak: " + peakTemp + "C, Peaked at: " + peak.format(timeFormatter) +
                ", Average: " + avgTemp + "C");
    }

    @Override
    public void peak() { peakTemp = stats.GetPeakTemp(); }

    public void avg() {
        avgTemp = stats.GetAverageTemps();
    }
}
