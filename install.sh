#!/usr/bin/bash

currentdir=$(pwd)

mkdir -p ~/.local/bin/gmon_parser/src/
cp $currentdir/* ~/.local/bin/gmon_parser 2> /dev/null
cp -r $currentdir/src/scripts/ ~/.local/bin/gmon_parser/src/
cp $currentdir/resources/gmon ~/.local/bin 2> /dev/null
cp $currentdir/resources/gmon_parser.jar ~/.local/bin/gmon_parser 2> /dev/null
cp $currentdir/resources/gmon.desktop ~/.local/share/applications/gmon.desktop
chmod +x ~/.local/bin/gmon
chmod +x ~/.local/bin/gmon_parser/src/scripts/LogTemp
chmod +x ~/.local/bin/gmon_parser/src/scripts/LogFanSpeed