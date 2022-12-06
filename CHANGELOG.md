# Changelog
___
Notable changes to the project will be documented in this file.

# [v1.2.0-Unreleased]
___

Small updates to functionality of the app.

### Added

- "Show gmon" option in system tray menu.

### Changed

- gmon now completely hides when closed unless "Show gmon" is pressed.

### Known Issues

- On GNOME desktops, Xorg must be run as root for Coolbits and overclocking/fan control to work. This can be a security risk due to escalating privileges, but there is no current workaround. [Link](https://github.com/NVIDIA/nvidia-settings/issues/65#issuecomment-832921061) to Nvidia github where the solution is discussed.

# [v1.1.0] - 2022-07-26
___
Small updates to the look and licensing of the app.

### Added

- Project is now licensed under the Mozilla Public License 2.0 (MPL2.0)
- System tray icon.
- An example config to load as default.

### Changed

- TimePanel reworked to look more similar to TemperaturePanel and FanSpeedPanel.
- Refactored code to look cleaner.
- Boot.java renamed to gmon.java so that app titles appear consistent.
- Hid averages, peaks, and peak times in tooltips.
- Gave the stat panels bevels.

### Known Issues

- Trying to load a config with only a single fan curve point causes a crash.
- System tray icon is blank instead of displaying the gmon logo.

# [1.0.0] - 2022-07-24
___



### Added

- gmon can now control GPU fan speed using values specified in the config.
- Fan curve smoothing added.
- Added CHANGELOG.md to repository.
- Added basic config support.
- Added install.sh
- Tracking for peaks, peak times, averages, and elapsed time.
- Basic GUI.
- Added the bash script to repository.
- Added fan speed parsing.
- Basic temp parsing.

### Changed

- Fan curve profiles can now be loaded from the config file.
- Refactored parts of ValueExtract into Statistics.
- Time now tracked using LocalTime and currentTimeMillis().
- Bash script refactored into two separate scripts.
- Changed GUI backend structure to be more object oriented.
- Removed CLI components.
- Restructured packaging to be more conducive for an application with a GUI.
- Reworked TokenizeStrings() to FormatStrings().
- Refactored Main and ValueExtract to work with the new log types.
- Split the nvidia-smi call into two separate calls.
- Clarified an error that can occur when opening the log file.
- CLI display formatted better.

### Fixed

- ValueExtract is now stat agnostic.
- The program is now user agnostic.
