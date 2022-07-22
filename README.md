# gmon_parser

A tool used to parse the output of nvidia-smi

# What's with the name?

gmon was an abbreviation of GPU monitor, and when I made the gmon script, I didn't feel like having to type "GPU\ Monitor" every time I wanted to run it.

# But why though?

GreenWithEnvy is a nice set of tools, but I had a few issues with it, and wanted to try my own hand at it.

Simply run a while loop and query nvidia-smi every two seconds, while both displaying the output to a terminal and writing it to
a log file. When done, shut down the script, execute the gmon_parser.jar and bam, all the stats, right there.

# Usage:

First, make sure you're using an Nvidia GPU and the proprietary drivers. It will not work on an AMD card, and I haven't tested it on the Nouveau drivers, though I don't believe it'd work on those either.<br>

Make sure that the bash script, gmon, is executable by using:

    chmod +x gmon

After that use:

    ./gmon

and that should get you logging. It'll continuously display the information from the drivers, so you can keep the terminal open on another screen or in the background as you do things. When done, use Ctrl + C to interrupt it, and you can move on to using the parser with:

    java -jar gmon_parser.jar

Use that, and it will display the parsed information in the terminal. If you wanted to make life easier on yourself, you could move gmon into /usr/local/bin so that you can execute it anywhere, without needing to type in the path to it.