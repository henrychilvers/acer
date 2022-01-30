package main

/**
 * Advent Calendar Enjoyment Randomizer.
 * Copyright (c) 2017 - 2022.
 *
 * @author Henry Chilvers
 */
func main() {
	printer := Printer{}

	printer.PrintOpeningScreen()

	game := New(printer)

	printer.PrintPlayerTable(game)

	game.ChooseDaysToOpen(printer)

	printer.PrintFinalResults(game)
}
