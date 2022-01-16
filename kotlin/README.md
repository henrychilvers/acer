# A.C.E.R. - The Advent Calendar Enjoyment Randomizer

[![Current Version](https://img.shields.io/badge/version-1.0.1-green.svg)](https://github.com/henrychilvers/acer)
[![GitHub Stars](https://img.shields.io/github/stars/henrychilvers/SNES.svg)](https://github.com/henrychilvers/acer/stargazers)
[![GitHub Issues](https://img.shields.io/github/issues/henrychilvers/SNES.svg)](https://github.com/henrychilvers/acer/issues)
[![License](https://img.shields.io/badge/License-BSD_3--Clause-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)

## Motivation
Our family likes to do Lego Advent Calendar (https://shop.lego.com/en-US/LEGO-Star-Wars-Advent-Calendar-75184), and there are 3 kids. So we've had to figure out how to fairly randomize
who gets to pick which door to open.

This is the Kotlin port of my original Java 11 version of A.C.E.R.

## Screenshots
Include logo/demo screenshot etc.

### Tech used
- Kotlin 1.6/Java 16
- Gradle 7.1

## Features
1. Choose the number of participants, and enter their names
2. Enter number of days on your particular advent calendar

### Build
```
gradlew jar
```

### Usage
```
java -jar acer.jar
```

## Credits
- ASCII Art Generator: www.quickprogrammingtips.com
- ASCII Table formatter: https://github.com/vdmeer/asciitable#maven
- Randomizing collections: https://stackoverflow.com/questions/20058366/shuffle-a-list-of-integers-with-java-8-streams-api

### Todo
1. Add spinning cursor
2. Internationalize?

## License
Usage is provided under the [BSD 3 Claus License](https://opensource.org/licenses/BSD-3-Clause). See LICENSE for the full details.