# Changelog
___
Notable changes to the project will be documented in this file.

# [v1.0.1-Unreleased] - 2022-07-25
___
Small updates to the look of the app.

### Added

- System tray icon.
- An example config to load as default.

### Changed

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