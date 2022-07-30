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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

// V2.0.0 TODO: Get whether user is using light or dark mode, apply colors based on that.
public class Main extends JFrame {
    private final GUI gui;
    private final ValueExtract ve;
    private final Statistics stats;
    private final ExecScripts execScripts;
    private final FanControl fCon;

    private final String userName = System.getProperty("user.name");
    private final SystemTray systemTray;

    private Image icon;

    private final Color[] colors = new Color[4];

    public Main(String title) {
        this.setTitle(title);

        // colors[0] is background, [1] is foreground, [2] is critical, and [3] is approaching critical
        //
        colors[0] = Color.DARK_GRAY; colors[1] = Color.LIGHT_GRAY; colors[2] = Color.RED; colors[3] = Color.YELLOW;

        stats = new Statistics();
        ve = new ValueExtract(stats);
        execScripts = new ExecScripts();
        fCon = new FanControl(stats, execScripts);
        gui = new GUI(stats, colors);
        systemTray = SystemTray.getSystemTray();

        icon = Toolkit.getDefaultToolkit().getImage("/home/" + userName + "/.local/bin/gmon_parser/gmon_logo.png");

        init();
        update();
    }


    //------------------------------------------------------------------------------------------------------------------
    // init methods.


    private void init() {
        setLayout(new BorderLayout());

        // DefaultCloseOperation set to DO_NOTHING currently. An option for the tray icon to open gmon needs to be added,
        // then it will be est to HIDE_ON_CLOSE, and that option would unhide it.
        //
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(200,300);
        setBackground(colors[0]);
        gui.setBackground(colors[0]);

        this.setIconImage(icon);

        InitSystemTray();

        getContentPane().add(gui);
        this.setVisible(true);
    }

    private void InitSystemTray() {
        PopupMenu trayPopupMenu = new PopupMenu();
        MenuItem menuQuit = new MenuItem("Quit");
        MenuItem menuShow = new MenuItem("Show gmon");

        menuShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Unhide();
            }
        });

        menuQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        trayPopupMenu.add(menuShow);
        trayPopupMenu.add(menuQuit);
        TrayIcon gmonIcon = new TrayIcon(icon, "gmon", trayPopupMenu);
        gmonIcon.setImageAutoSize(true);

        try {
            systemTray.add(gmonIcon);
            gmonIcon.setImage(icon);

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }


    //------------------------------------------------------------------------------------------------------------------


    private void ExecScript() {
        execScripts.LogTemp();
        execScripts.LogFSpeed();
    }

    public void Unhide() {
        setVisible(true);
    }

    public void quit() {
        execScripts.quit();
        System.exit(0);
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
