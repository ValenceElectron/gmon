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
import java.io.*;

// TODO: Refactor Main into a singleton.
// TODO: Add another option on TrayIcon to unhide gmon.

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

    private final Color bgColor = Color.DARK_GRAY;
    private final Color fgColor = Color.LIGHT_GRAY;

    public Main(String title) {
        this.setTitle(title);

        stats = new Statistics();
        ve = new ValueExtract(stats);
        execScripts = new ExecScripts();
        fCon = new FanControl(stats);
        gui = new GUI(stats, bgColor, fgColor);
        systemTray = SystemTray.getSystemTray();

        icon = Toolkit.getDefaultToolkit().getImage("/home/" + userName + "/.local/bin/gmon_parser/gmon_logo.png");

        init();
        update();
    }

    //-----------------------------------------------------------------------------------------------------------------

    private void init() {
        setLayout(new BorderLayout());

        // DefaultCloseOperation set to DO_NOTHING currently. An option for the tray icon to open gmon needs to be added,
        // then it will be est to HIDE_ON_CLOSE, and that option would unhide it.
        //
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(200,300);
        setBackground(bgColor);
        gui.setBackground(bgColor);

        SetupWindowListener();

        this.setIconImage(icon);

        InitSystemTray();

        getContentPane().add(gui);
        this.setVisible(true);
    }

    private void InitSystemTray() {
        PopupMenu trayPopupMenu = new PopupMenu();
        MenuItem menuQuit = new MenuItem("Quit");
        menuQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
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

    private void SetupWindowListener() {

        // If someone tries to exit gmon once, just minimize it. If they try again, quit.
        //
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (getExtendedState() == Frame.ICONIFIED) {
                    quit();
                }
                setExtendedState(Frame.ICONIFIED);
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------

    private void ExecScript() {
        execScripts.LogTemp();
        execScripts.LogFSpeed();
    }

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

    public void quit() {
        fCon.quit();
        System.exit(0);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
