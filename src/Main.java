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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(200,300);
        setBackground(bgColor);
        gui.setBackground(bgColor);

        SetupWindowListener();

        this.setIconImage(icon);
        //System.out.println("Set application icon");

        InitSystemTray();

        getContentPane().add(gui);

        this.setVisible(true);
    }

    private void InitSystemTray() {
        //System.out.println("Initializing tray icon.");
        //System.out.println(systemTray.isSupported());
        PopupMenu trayPopupMenu = new PopupMenu();
        MenuItem menuQuit = new MenuItem("Quit");
        menuQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        trayPopupMenu.add(menuQuit);
        TrayIcon trayIcon = new TrayIcon(icon, "gmon", trayPopupMenu);
        trayIcon.setImageAutoSize(true);

        try {
            systemTray.add(trayIcon);
            trayIcon.setImage(icon);

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private void SetupWindowListener() {
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
