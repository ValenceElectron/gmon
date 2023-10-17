
# gmon v1.1.0

Copyright (c) ValenceElectron

This file is part of the gmon_parser project.

This project has not been tested on environments outside of my own desktop yet.

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.

A copy of the MPL was included in LICENSE.txt
The source code can be found at: https://github.com/ValenceElectron/gmon_parser


# What is gmon?

gmon is a tool allowing you to monitor and control Nvidia GPU fan speeds.

# What's with the name?

A mix of "GPU" and "monitor".

# Dependencies: 

- openjdk-17
- Nvidia driver version 515 (this may work on older drivers, I haven't tested them).
- X11

# Installing:

- Download the zip or tar.xz from the releases tab
- Extract the files from the zip or tar.xz
- run 'chmod +x install.sh' to give it executable permissions
- run './install.sh'

### If 'install.sh' fails

- Copy gmon_v1.1.0 to ~/.local/bin/ and rename the directory to 'gmon_parser'
- Navigate to the 'resources' folder and move all the files into the parent 'gmon_parser' directory.
- copy 'gmon' to ~/.local/bin/
- copy 'gmon.desktop' to ~/.local/share/applications/

# Usage: 

- 'gmon' in the terminal if ~/.local/bin/ is part of your $PATH.
- The gmon app from your applications launcher (Super and search "gmon").
- You can run it directly by navigating to where gmon is installed and running "java -jar gmon_parser.jar"

# Configuring Fan Curve:

gmon doesn't currently have UI support for modifying the fan curves. To modify the fan curve, navigate to your gmon installation
and open 'gmon.conf'. Points on the curve are stored as GPUFanPx,Celsius,FanSpeed%. Ensure that there are at least two points, since the algorithm requires at least two to make a curve.

# Afterword

This is my first OSS project, and I built it specifically because it was an app that I needed while I was running an Nvidia GPU.
I have since moved to AMD with the RX 6900 XT, and this project has moved to the backburner.
