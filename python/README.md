## Project title
A.C.E.R. - The Advent Calendar Enjoyment Randomizer

[![Current Version](https://img.shields.io/badge/version-1.0.1-green.svg)](https://github.com/henrychilvers/acer)
[![GitHub Stars](https://img.shields.io/github/stars/henrychilvers/SNES.svg)](https://github.com/henrychilvers/acer/stargazers)
[![GitHub Issues](https://img.shields.io/github/issues/henrychilvers/SNES.svg)](https://github.com/henrychilvers/acer/issues)
[![License](https://img.shields.io/badge/License-BSD_3--Clause-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)

## Motivation
Our family likes to do Lego Advent Calendar (https://shop.lego.com/en-US/LEGO-Star-Wars-Advent-Calendar-75184), and there are 3 kids. So we've had to figure out how to fairly randomize
who gets to pick which door to open.

This is the Python port of my original Java 11 version of A.C.E.R.

## Screenshots
Include logo/demo screenshot etc.

## Tech/framework used
- Python 3.9

## Features
1. Choose number of participants
2. Enter number of days our your particular advent calendar

## Build
Automatically install the listed dependencies by running the following command:
```shell
python -m pip install -r requirements.txt
```
## Run Unit Tests
```shell
python -m pytest tests
```
## How to use?
```shell
python -m acer
```

## Credits
- colorama https://github.com/tartley/colorama
- shellingham https://github.com/sarugaku/shellingham
- pytest https://docs.pytest.org/en/latest/
- tabulate https://github.com/astanin/python-tabulate
- pyfiglet https://github.com/pwaller/pyfiglet
- termcolor2 https://github.com/v2e4lisp/termcolor2

## License
Usage is provided under the [BSD 3 Claus License](https://opensource.org/licenses/BSD-3-Clause). See LICENSE for the full details.