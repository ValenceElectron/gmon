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

// Version 1.1.0

import control.FanControl;
import scripts.ExecScripts;
import stats.Statistics;
import stats.ValueExtract;
import ui.GUI;
import javax.swing.*;
import java.awt.*;
import java.io.*;

// V2.0.0 TODO: Get whether user is using light or dark mode, apply colors based on that.
public class Main extends JFrame {
    private final GUI gui;
    private final ValueExtract ve;
    //private final Statistics stats;
    private final ExecScripts execScripts;
    private final FanControl fCon;

    private final Color[] colors = new Color[4];

    public Main(String title) {
        Statistics stats;
        this.setTitle(title);

        // colors[0] is background, [1] is foreground, [2] is critical, and [3] is approaching critical
        //
        colors[0] = Color.DARK_GRAY; colors[1] = Color.LIGHT_GRAY; colors[2] = Color.RED; colors[3] = Color.YELLOW;

        stats = new Statistics();
        ve = new ValueExtract(stats);
        execScripts = new ExecScripts();
        fCon = new FanControl(stats, execScripts);
        gui = new GUI(stats, colors);

        init();
        update();
    }


    //------------------------------------------------------------------------------------------------------------------
    // init methods.


    private void init() {
        setLayout(new BorderLayout());

        // EXIT_ON_CLOSE uses System.exit and quits the program
        //
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,300);
        setBackground(colors[0]);
        gui.setBackground(colors[0]);

        getContentPane().add(gui);
        this.setVisible(true);
    }

    //------------------------------------------------------------------------------------------------------------------


    private void ExecScript() {
        execScripts.LogTemp();
        execScripts.LogFSpeed();
    }


    //------------------------------------------------------------------------------------------------------------------
    // update methods.

    public void update() {
        while (true) {
            try {
                ExecScript();
                ve.update();
                fCon.update();
                gui.update();
            } catch (IOException e) { throw new RuntimeException(e); }

            try {
                // Want it to update every second. If that needs to be slower, simply increase this.
                //
                Thread.sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace();}
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
