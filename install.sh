#!/usr/bin/bash

currentdir=$(pwd)

mkdir -p /home/valence/.local/bin/gmon_parser/src/
cp $currentdir/* /home/$USER/.local/bin/gmon_parser 2> /dev/null
cp -r $currentdir/src/scripts/ ~/.local/bin/gmon_parser/src/ 2> /dev/null

cp $currentdir/resources/gmon ~/.local/bin 2> /dev/null
cp $currentdir/resources/gmon_parser.jar ~/.local/bin/gmon_parser 2> /dev/null
cp $currentdir/resources/gmon_logo.png ~/.local/bin/gmon_parser 2> /dev/null
cp $currentdir/resources/gmon.conf ~/.local/bin/gmon_parser 2> /dev/null
cp $currentdir/resources/gmon.desktop ~/.local/share/applications/gmon.desktop 2> /dev/null
echo Exec=/home/$USER/.local/bin/gmon >> ~/.local/share/applications/gmon.desktop
echo Icon=/home/$USER/.local/bin/gmon_parser/gmon_logo.png >> ~/.local/share/applications/gmon.desktop

echo Example config placed in install directory, remove gmon.conf for system default fan curve.

chmod +x ~/.local/bin/gmon
chmod +x ~/.local/bin/gmon_parser/src/scripts/LogTemp
chmod +x ~/.local/bin/gmon_parser/src/scripts/LogFanSpeed
