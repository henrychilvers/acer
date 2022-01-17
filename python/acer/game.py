import datetime
import random

from acer.player import Player


class Game:
    def __init__(self):
        self.gameTime = datetime.datetime.now()
        self.numberOfPlayers = 0
        self.numberOfDays = 0
        self.players = []
        self.shuffledDays = []

    def setup(self):
        self.setup_game()
        self.setup_players()

    def get_number_of_days_per_player(self):
        return self.numberOfDays / self.numberOfPlayers

    def get_player(self, random_player_position):
        return next((x for x in self.players if x.position == random_player_position), None)

    def still_has_available_days_to_pick(self):
        total_days_picked = 0

        for p in self.players:
            total_days_picked += len(p.days_to_choose)

        return total_days_picked < self.numberOfDays

    def setup_game(self):
        self.numberOfPlayers = int(input("Number of Players? "))
        self.numberOfDays = int(input("Number of Days? "))

        shuffled_days = list(range(1, self.numberOfDays + 1))

        # shuffle them N times...
        for r in range(1, self.numberOfDays + 1):
            random.shuffle(shuffled_days)

        self.shuffledDays = shuffled_days
        print()

    def setup_players(self):
        players = []

        for i in range(1, self.numberOfPlayers + 1):
            player_name = input(f"Name of Player {i}? ")
            player = Player(i, player_name)

            players.append(player)

        self.players = players
        print()

    def choose_days_to_open(self):
        while self.still_has_available_days_to_pick():
            random_player_position = random.randrange(1, self.numberOfPlayers + 1)
            current_player = self.get_player(random_player_position)

            if len(current_player.days_to_choose) == self.get_number_of_days_per_player():
                continue

            input(f"{current_player.name}, hit <enter> to pick a number... ")

            day_to_open = self.shuffledDays[0]
            print(f"{current_player.name}, you got day {day_to_open}")
            current_player.days_to_choose.append(day_to_open)

            self.shuffledDays.pop(0)
            if len(self.shuffledDays) == 0:
                print("\n[All Days Picked!]\n")
            else:
                print(f"[Days remaining: {len(self.shuffledDays)}]\n")
