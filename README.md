
# gmon v1.1.0
___

Copyright (c) ValenceElectron

This file is part of the gmon_parser project.

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.

A copy of the MPL was included in LICENSE.txt
The source code can be found at: https://github.com/ValenceElectron/gmon_parser


# What is gmon?
___

gmon is a work-in-progress suite of GPU monitoring and control tools for Nvidia graphics cards.

# What's with the name?
___

gmon was an abbreviation of "GPU monitor", and this project started  out predominantly as an actual logging tool. It required a bash script that I had made called "gmon" which called "nvidia-smi"
every 2 seconds and saved the output into a log file called gputemps.log. The Java program came in when I wanted an easy way to sift through and process all that data.

Following that, I made continuous improvements to it and now it's becoming a fully-fledged GPU control app.
It currently features temperature monitoring, fan speed monitoring, fan curve control, and reporting peaks, averages, and peak times. More features to follow.

# But why though? 
___

GreenWithEnvy is a nice set of tools, but I had a few issues with it, and wanted to try my own hand at making GPU monitoring
and control software.

# Dependencies: 
___

- openjdk-17
- Nvidia driver version 515 (this may work on older drivers, I haven't tested them).
- Must be using X11

For new Linux users, here's how to deal with dependencies: as a general rule of thumb, you can
track down the dependencies based on the name given to you in sections such as this. Usually what
exactly you're looking for will depend on your distro due to different distros calling packages slightly
different names.

When it comes to Java 17, here are the package names from some of the bigger distros:

- Ubuntu: openjdk-17-jdk <br>
  Most Ubuntu derivatives should also use this same package name. Things like Pop!_OS, Linux Mint,
  and others. To install this package, open a terminal using either the keyboard shortcuts or searching
  for it in your Applications menu, and run the command 'sudo apt-get install openjdk-17-jdk'. Follow
  the prompts and you're all set.

- Fedora: java-17-openjdk.x86_64 <br>
  This works for Fedora and some of its derivatives. Can confirm it works on Nobara Linux.To install
  this package, open a terminal using either the keyboard shortcuts or searching for it in your
  Applications menu, and run the command 'sudo dnf install java-17-openjdk.x86_64.'

- Arch:   jre17-openjdk <br>
  I believe this is in AUR, so it should be available to Arch and its derivatives, assuming derivatives
  also use AUR. To install this package, open a terminal using either the keyboard shortcuts or
  searching for it in your Applications menu, and run the command 'sudo pacman -S jre17-openjdk'.

Installing the proprietary Nvidia drivers is a bit more of a spicy topic. Lots of different ways to do it,
it comes pre-installed with some distros, others require settings being toggle to be able to get them. I
recommend doing a cursory search for how to install them on your distro and then coming back.

If you're not sure whether you have them or not, go ahead open a terminal, and run the command 'nvidia-smi'.
If you have them, a lot of stuff will be written in the terminal, and at the very top it should list the
current driver version.

Finally, the last dependency. You must be using X11 and not Wayland, due to Nvidia not terribly caring for
the Linux scene. If you're on Wayland, or you're not sure what you're using, simply log out of your
desktop and before logging back in, look for a settings icon on the login screen. Click it, and choose the
option that says 'GNOME (on X11)' or something to that effect. Once done, you're all set.


# Installing:
___

First, make sure you're using an Nvidia GPU and the proprietary drivers. It will not work on an AMD card,
and I haven't tested it on the Nouveau drivers, but I'm fairly certain it won't work with that either.

To install gmon, simply open a terminal by either using the keyboard shortcuts or searching for it in
Applications, navigate to the directory where gmon was extracted, run 'chmod +x install.sh' to give it
executable permissions, then run './install.sh'.

install.sh is just a simple bash script that will move everything where it should be in ~/.local/bin and
~/.local/share/applications. Always make sure to thoroughly checkout whatever you're giving executable
permissions, though.


# Usage: 
___

To use gmon, simply run the 'gmon' command in a terminal, or search your Applications for 'gmon'. It will
load up a preconfigured fan curve for you automatically, and you'll see a screen tracking your temperatures,
fan speed, and time elapsed since gmon was started.

As of v1.1.0, the only way to configure the fan curve is to navigate to ~/.local/bin/gmon_parser, and
edit gmon.conf. Simply open it with any text editor, and you should see a few variables and their values.
Double check and make sure that GPUFanControlState is equal to 1 (a 0 would load your GPU's default fan
curve), then you can edit the fan curve points how you'd like.

Currently, there's not much validation on the config, so try and make sure there are always at least 2
points (GPUFanP0 and GPUFanP1) in the config, that the name of the points and the values are separated by
commas (no spaces), and that the values are a valid integer (whole number). The first number is temperature,
the second number is the percentage of max fan speed.

# Afterword
___

This is my first real project, and I had to learn how most thing work on my own. If you have any issues,
you can open an issue on the Github page, but if you wanted a quicker way to contact me about a potentially big
and/or breaking issue, sending a tweet or email my way will usually get to me faster. Ideally, both open a Github issue
and tweet at me, that way I get notified quick and it's tracked on Github. If you need places to find me check them out
below. Do note, I'm not in a habit of checking my email frequently, but I will setup push notifications on my phone for it.

- [Github](https://github.com/ValenceElectron)
- [Twitter](https://twitter.com/TheLastElectron)
- TheLastElectronSPDF@gmail.com