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

package scripts;

import java.io.File;
import java.io.IOException;

public class ExecScripts {
    final private String userName = System.getProperty("user.name");
    final private String workingDir = "/home/" + userName + "/.local/bin/gmon_parser";
    final private String tempMon = workingDir+"/src/scripts/LogTemp";
    final private String fSpeedMon = workingDir+"/src/scripts/LogFanSpeed";
    final private String fanCon = workingDir+"/src/scripts/fanCon";

    // All the methods in this class run Bash scripts that help log values to track, or control fan speeds.
    //
    public ExecScripts() {

    }

    public void LogTemp() {
        Process p;
        try{
            String[] cmd = {"sh", tempMon};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void LogFSpeed() {
        Process p;
        try{
            String[] cmd = {"sh", fSpeedMon};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateFanSpeed(String GPUFanControlState, String GPUFanTargetSpeed) {
        Process p;
        try{
            String[] cmd = {"sh", fanCon, GPUFanControlState, GPUFanTargetSpeed};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void quit() {
        // When quit, we want gmon to allow the driver controlled fan curve to take over again.
        //
        Process p;
        try{
            String[] cmd = {"sh", fanCon, "1", "38"};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
