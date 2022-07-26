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

package stats;

import java.io.*;

public class ValueExtract {
    private final Statistics stats;

    private BufferedReader tempText;
    private BufferedReader fSpeedText;

    private String tempLine;
    private String fSpeedLine;

    final private String userName = System.getProperty("user.name");
    final private String workingDir = "/home/" + userName + "/.local/bin/gmon_parser/logs";
    final private File inputTemp = new File(workingDir, "gputemps.log");
    final private File inputFSpeeds = new File(workingDir, "gpufspeed.log");

    private Boolean isTempOpen = false;
    private Boolean isFSpeedOpen = false;

    public ValueExtract(Statistics stats) {
        this.stats = stats;
    }


    private void OpenFiles() throws FileNotFoundException {
        OpenTemps();
        OpenFSpeeds();
    }

    private void OpenTemps() throws FileNotFoundException {

        // In the off chance OpenTemps() gets called twice at the same time. Don't want to run into
        // any problems with multiple things trying to read from the same file.
        //
        if (isTempOpen)
            return;

        tempText = new BufferedReader((new FileReader(inputTemp)));
        isTempOpen = true;
    }

    private void OpenFSpeeds() throws FileNotFoundException {

        // In the off chance OpenFSpeeds() gets called twice at the same time. Don't want to run into
        // any problems with multiple things trying to read from the same file.
        //
        if (isFSpeedOpen)
            return;

        fSpeedText = new BufferedReader((new FileReader(inputFSpeeds)));
        isFSpeedOpen = true;
    }

    private void LogsToStrings() throws IOException {
        TempLogToString();
        FSpeedLogToString();
    }

    private void TempLogToString() throws IOException {
        tempLine = tempText.readLine();
        tempText.close();
        isTempOpen = false;
    }

    private void FSpeedLogToString() throws IOException {
        fSpeedLine = fSpeedText.readLine();
        fSpeedText.close();
        isFSpeedOpen = false;
    }

    private void FormatStrings() {
        FormatTemps();
        FormatFSpeeds();
    }

    private void FormatTemps() {
        int currentValue = Integer.parseInt(tempLine);
        stats.AddTemp(currentValue);
    }

    private void FormatFSpeeds() {
        int currentValue = Integer.parseInt(fSpeedLine.substring(0, fSpeedLine.length()-2));
        stats.AddFSpeed(currentValue);
    }

    public void update() throws IOException {
        try { OpenFiles(); }
        catch (Exception e) { System.out.println("File could not be opened. Perhaps no log is generated?"); }
        LogsToStrings();
        FormatStrings();
    }
}