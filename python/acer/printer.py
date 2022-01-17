import os

import pyfiglet
from tabulate import tabulate


class Printer:
    WELCOME = "Welcome to..."
    APP_NAME = "A.C.E.R."
    NUMBER_OF_PLAYERS_PROMPT = "Number of players? "
    NUMBER_OF_DAYS_PROMPT = "Number of days in calendar? "
    RESULTS = "Results..."
    READY_PROMPT = "Ready? Here we go... "
    FAREWELL = "Thanks for using A.C.E.R.! Enjoy your advent calendar!"

    def print_opening_screen(self):
        self.clear_screen()
        self.print_welcome()
        self.print_logo()

    def print_final_results(self, game):
        self.clear_screen()
        self.print_logo()
        self.print_results()
        self.print_player_table(game)
        self.print_farewell()

    @staticmethod
    def clear_screen():
        os.system('cls' if os.name == 'nt' else 'clear')

    def print_welcome(self):
        print(self.WELCOME)

    def print_logo(self):
        print()
        print(pyfiglet.figlet_format(self.APP_NAME, font="banner3-D"))
        print(" ~ Advent        Calendar       Enjoyment      Randomizer ~")
        print()

    def print_ready(self):
        print(self.READY_PROMPT)
        print()

    def print_results(self):
        print(self.RESULTS)

    @staticmethod
    def print_player_table(game):
        headers = ["Player", "Days to Open"]
        table = []

        for p in game.players:
            row = [p.name, p.final_results()]
            table.append(row)

        print(tabulate(table, headers, tablefmt="fancy_grid"))
        print()

    def print_farewell(self):
        print(self.FAREWELL)

    def print_number_of_players_prompt(self):
        print(self.NUMBER_OF_PLAYERS_PROMPT)

    def print_number_ofe_days_prompt(self):
        print(self.NUMBER_OF_DAYS_PROMPT)

    @staticmethod
    def print_player_name_prompt(i):
        print(f"Name of Player {i}? ")

    @staticmethod
    def print_player_turn_result(player_name, day_to_open):
        print(f"{player_name}, you got day {day_to_open}")
        print()
