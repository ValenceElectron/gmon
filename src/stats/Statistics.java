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

import java.util.ArrayList;

public class Statistics {
    private ArrayList<Integer> tempsList;
    private ArrayList<Integer> fSpeedsList;

    private int peakTemp;
    private int peakFSpeed;
    private int peakIndex;

    public Statistics() {
        tempsList = new ArrayList<>();
        fSpeedsList = new ArrayList<>();
        peakTemp = 0;
        peakFSpeed = 0;
    }

    public void AddTemp(int temp) {
        tempsList.add(temp);
        DetermineTempPeak();
    }

    public void AddFSpeed(int fspeed) {
        fSpeedsList.add(fspeed);
        DetermineFSpeedPeak();
    }

    @Deprecated
    public int GetCurrentTemp() { return tempsList.get(tempsList.size() - 1); }

    @Deprecated
    public int GetCurrentFSpeed() { return fSpeedsList.get(fSpeedsList.size() - 1); }

    public int GetCurrent(String parameter) {
        if (parameter.equals("Temperature"))
            return tempsList.get(tempsList.size() -1);
        else if (parameter.equals("Fan Speed"))
            return fSpeedsList.get(fSpeedsList.size() - 1);
        else
            return -1;
    }

    @Deprecated
    public int GetPeakTemp() { return peakTemp; }

    @Deprecated
    public int GetPeakFSpeed() { return peakFSpeed; }

    @Deprecated
    public int GetPeakIndex() { return peakIndex; }

    // This is to make the GUI panels more object oriented
    //
    public int GetPeak(String parameter) {
        if (parameter.equals("Temperature"))
            return peakTemp;
        else if (parameter.equals("Fan Speed"))
            return peakFSpeed;
        else if (parameter.equals("Time Elapsed"))
            return peakIndex;
        else
            return -1;
    }

    @Deprecated
    public int GetAverageTemps() {
        int sum = 0;
        for (int i = 0; i < tempsList.size(); i++) {
            sum += tempsList.get(i);
        }

        return sum/tempsList.size();
    }

    @Deprecated
    public int GetAverageFSpeeds() {
        int sum = 0;
        for (int i = 0; i < fSpeedsList.size(); i++) {
            sum += fSpeedsList.get(i);
        }

        return sum/fSpeedsList.size();
    }

    // This is to make the GUI panels more object oriented
    //
    public int GetAverage(String parameter) {
        if (parameter.equals("Temperature")) {
            int sum = 0;
            for (int i = 0; i < tempsList.size(); i++) {
                sum += tempsList.get(i);
            }

            return sum/tempsList.size();
        } else if (parameter.equals("Fan Speed")) {
            int sum = 0;
            for (int i = 0; i < fSpeedsList.size(); i++) {
                sum += fSpeedsList.get(i);
            }

            return sum/fSpeedsList.size();
        } else
            return -1;
    }

    private void DetermineTempPeak() {
        if (peakTemp <= tempsList.get(tempsList.size() - 1)) {
            peakTemp = tempsList.get(tempsList.size() - 1);
            peakIndex = tempsList.size() - 1;
        }
    }

    private void DetermineFSpeedPeak() {
        if (peakFSpeed <= fSpeedsList.get(fSpeedsList.size() - 1)) peakFSpeed = fSpeedsList.get(fSpeedsList.size() - 1);
    }
}
