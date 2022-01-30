package main

import (
	"fmt"
	"os"

	"github.com/common-nighthawk/go-figure"
	"github.com/inancgumus/screen"
	"github.com/olekukonko/tablewriter"
)

const (
	welcome               = "Welcome to..."
	appName               = "A.C.E.R."
	numberOfPlayersPrompt = "Number of players? "
	numberOfDaysPrompt    = "Number of days in calendar? "
	results               = "Results..."
	readyPrompt           = "Ready? Here we go... "
	farewell              = "Thanks for using A.C.E.R.! Enjoy your advent calendar!"
)

type Printer struct {
}

func (p Printer) PrintOpeningScreen() {
	clearScreen()
	printWelcome()
	printLogo()
	printReady()
}

func (p Printer) PrintFinalResults(game Game) {
	clearScreen()
	printLogo()
	printResults()
	p.PrintPlayerTable(game)
	printFarewell()
}

func clearScreen() {
	screen.Clear()
}

func printWelcome() {
	fmt.Println(welcome)
}

func printLogo() {
	fmt.Println()
	myFigure := figure.NewFigure(appName, "isometric1", true)
	myFigure.Print()
	fmt.Println("( Advent           Calendar          Enjoyment        Randomizer )")
	fmt.Println()
}

func printReady() {
	fmt.Println(readyPrompt)
}

func (p Printer) PrintNumberOfPlayersPrompt() {
	fmt.Print(numberOfPlayersPrompt)
}

func (p Printer) PrintNumberOfDaysPrompt() {
	fmt.Print(numberOfDaysPrompt)
}

func (p Printer) printRequestPlayerInteraction(currentPlayerName string) {
	fmt.Printf("%s, hit <enter> to pick a number... ", currentPlayerName)
}

func (p Printer) printPlayerTurnResult(playerName string, dayToOpen int) {
	fmt.Printf("%s, you got day %d", playerName, dayToOpen)
	fmt.Println()
}

func (p Printer) printDaysRemaining(daysRemaining int) {
	if daysRemaining == 0 {
		fmt.Println("\n[All Days Picked!]")
	} else {
		fmt.Printf("[Days remaining: %d]\n", daysRemaining)
	}
	fmt.Println()
}

func printResults() {
	fmt.Println(results)
}

func (p *Printer) PrintPlayerTable(game Game) {
	table := tablewriter.NewWriter(os.Stdout)
	table.SetHeader([]string{"Player", "Days to Open"})

	for _, p := range game.Players {
		var row = []string{p.name, p.finalResults()}
		table.Append(row)
	}

	table.Render()
	fmt.Println()
}

func printFarewell() {
	fmt.Println()
	fmt.Println(farewell)
}
