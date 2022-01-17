"""A.C.E.R. entry point script."""
# acer/__main__.py

from acer.game import Game
from acer.printer import Printer


def main():
    printer = Printer()
    game = Game()

    printer.print_opening_screen()
    game.setup()
    # printer.print_player_table(game)
    printer.print_ready()
    game.choose_days_to_open()
    printer.print_final_results(game)


if __name__ == "__main__":
    main()
