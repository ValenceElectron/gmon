# gmon_parser

A work-in-progress suite of GPU monitoring and control tools for Nvidia graphics cards.

# What's with the name?

gmon was an abbreviation of "GPU monitor", and this project started  out predominantly as an actual logging tool. It required a bash script that I had made called "gmon" which called "nvidia-smi"
every 2 seconds and saved the output into a log file called gputemps.log. The Java program came in when I wanted an easy way to sift through and process all that data.
Following that, I made continuous improvements to it and now it's becoming a fully-fledged GPU control app.

# But why though?

GreenWithEnvy is a nice set of tools, but I had a few issues with it, and wanted to try my own hand at it.

# Dependencies:

- openjdk-17
- Nvidia driver version 515 (this may work on older drivers, I haven't tested them).

# Usage:

First, make sure you're using an Nvidia GPU and the proprietary drivers. It will not work on an AMD card, and I haven't tested it on the Nouveau drivers, though I don't believe it'd work on those either.<br>

Open up a terminal, navigate to the directory that you'd like to house the repository, and clone it using:

    git clone https://github.com/ValenceElectron/gmon_parser.git

After that, head into the directory and give executable permissions to the install.sh (always double check any executable before giving it permissions, please. You never know if its malicious):

    cd gmon_parser
    chmod +x install.sh

The install script will then create a directory called gmon_parser in ~/.local/bin/ and move the requisite files there. Now, you could make sure that ~/.local/bin/ is in PATH, or you could
just search for "gmon" in your Applications menu, since it placed a .desktop file in ~/.local/share/applications/. Simply run gmon from either command line or the .desktop, and the monitoring services will start.