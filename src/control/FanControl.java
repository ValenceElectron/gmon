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

package control;

import scripts.ExecScripts;
import stats.Statistics;

import java.io.IOException;
import java.util.ArrayList;

public class FanControl {
    private final Statistics stats;
    private final ExecScripts execScripts;
    private LoadConfig cfg;

    final private String userName = System.getProperty("user.name");
    final private String workingDir = "/home/" + userName + "/.local/bin/gmon_parser";
    final private String fanCon = workingDir+"/src/scripts/fanCon";

    private int currentTemp;
    private ArrayList<Integer> tempThresholds;
    private ArrayList<Integer> fSpeedThresholds;
    private ArrayList<int[]> thresholds;

    public FanControl(Statistics stats, ExecScripts execScripts) {
        this.stats = stats; this.execScripts = execScripts;

        // Try/Catch here so that way the error doesn't propagate
        try {
            cfg = new LoadConfig();
        } catch (IOException e) {
           System.out.println("Could not load config.");
           e.printStackTrace();
        }

        init();
    }


    //------------------------------------------------------------------------------------------------------------------
    // init methods.


    private void init() {

        // GPUFanControlState=0 means driver controlled fan curve, so no need to bother with calculating/doing the rest.
        //
        if (cfg.GetGPUFanControlState().equals("0")) return;
        tempThresholds = cfg.GetTempThresholds();
        fSpeedThresholds = cfg.GetFSpeedThresholds();
        SmoothFanTransition();
    }

    /*
    This method takes an input of at least 2 values, and averages out 3 evenly spaced values between them.
    Three values are added per number of inputs, with the exception of only 1 input, where it'll throw an error.
    This is to smooth the fan speed transitions so that they're updated fairly frequently to keep up
    with the GPU temperature changes. If not for this, the default fan curve would only update fan speeds every
    15C or so.

    This currently runs every second, but it can be optimized with a method to check whether or not the current
    temperature is within the same fan curve bracket as the one that updated the fan speed. (i.e. between the same two
    points on the curve).
    */
    private void SmoothFanTransition() {
        System.out.println("Smoothing fan transitions...");
        int numOfVals = (tempThresholds.size() - 1) * 4;
        int[] tempArr = new int[numOfVals];
        int[] fSpeedArr = new int[numOfVals];
        int arrListIndex = 0;

        for (int i = 0; i < numOfVals - 1; i = i+4) {
            tempArr[i] = tempThresholds.get(arrListIndex);
            tempArr[i+2] = (tempThresholds.get(arrListIndex+1) + tempArr[i]) / 2;
            tempArr[i+1] = (tempArr[i] + tempArr[i+2]) / 2;
            tempArr[i+3] = (tempThresholds.get(arrListIndex+1) + tempArr[i+2]) / 2;

            fSpeedArr[i] = fSpeedThresholds.get(arrListIndex);
            fSpeedArr[i+2] = (fSpeedThresholds.get(arrListIndex+1) + fSpeedArr[i]) / 2;
            fSpeedArr[i+1] = (fSpeedArr[i] + fSpeedArr[i+2]) / 2;
            fSpeedArr[i+3] = (fSpeedThresholds.get(arrListIndex+1) + fSpeedArr[i+2]) / 2;
            arrListIndex++;
        }

        tempArr[numOfVals - 1] = tempThresholds.get(tempThresholds.size() - 1);
        fSpeedArr[numOfVals - 1] = fSpeedThresholds.get(fSpeedThresholds.size() - 1);

        thresholds = new ArrayList<>();

        for (int i = 0; i < tempArr.length; i++) {
            //System.out.println(tempArr[i] + ", " + fSpeedArr[i]);
            thresholds.add(new int[2]);
            thresholds.get(i)[0] = tempArr[i];
            thresholds.get(i)[1] = fSpeedArr[i];
        }
    }


    //------------------------------------------------------------------------------------------------------------------


    private void UpdateFanSpeed() {
        int index = DetermineCorrectFSpeed();
        execScripts.UpdateFanSpeed("1", Integer.toString(thresholds.get(index)[1]));
    }

    private int DetermineCorrectFSpeed() {
        for (int i = 0; i < thresholds.size(); i++) {
            if (currentTemp <= thresholds.get(i)[0]) {
                return i;
            }
        }

        // In the off change that currentTemp isn't <= to anything in thresholds.get[0], we want to the fans to get to max,
        // since that means they're greater than the max temperature handled by the fan curve.
        return thresholds.size() - 1;
    }


    //------------------------------------------------------------------------------------------------------------------
    // update methods.


    public void update() {

        // GPUFanControlState=0 means driver controlled fan curve, so no need to bother with calculating/doing the rest.
        //
        if (cfg.GetGPUFanControlState().equals("0")) return;
        currentTemp = stats.GetCurrentTemp();
        UpdateFanSpeed();
    }
}
