# gmon_parser
A tool used to parse the output of nvidia-smi

# But why though?
I wasn't too satisfied with using GreenWithEnvy's GPU monitoring tools. Don't get me wrong, it's an amazing bit of software and I use it,
but I want to actively monitor some things about my GPU without the added resource draw that GreenWithEnvy warns about. In comes my bash
script.

Simply run a while loop and query nvidia-smi every two seconds, while both displaying the output to a terminal and writing it to
a log file. When done, shut down the script, execute the gmon_parser.jar and bam, all the stats, right there.
