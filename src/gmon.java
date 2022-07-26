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

// This class exists simply for the possibility that I want to add boot arguments
public class gmon {

    // TODO: Go through and refactor things that seem too messy.
    // v2.0.0 TODO: Begin work on v2.0.0. v2 is going to focus on added more panels (VRAM usage, etc), clock speeds, and choosing panels to display.
    public static void main(String[] args) { new Main("gmon"); }
}