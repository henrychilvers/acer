package main

import (
	"errors"
	"fmt"
	"log"
	"math/rand"
	"strconv"
	"time"
)

type Game struct {
	//gameTime        time.Time `default:"time.Now()"`
	NumberOfPlayers       int `default:"0"`
	NumberOfDays          int `default:"0"`
	NumberOfDaysPerPlayer []int
	Players               []*Player
	ShuffledDays          []int
}

func New(p Printer) Game {
	var players []*Player

	p.PrintNumberOfPlayersPrompt()
	var numberOfPlayers int
	_, _ = fmt.Scanln(&numberOfPlayers)

	p.PrintNumberOfDaysPrompt()
	var numberOfDays int
	_, _ = fmt.Scanln(&numberOfDays)

	var shuffledDays = make([]int, numberOfDays)
	rand.Seed(time.Now().UnixNano())
	for i := 0; i < numberOfDays; i++ {
		shuffledDays[i] = i + 1
	}

	for i := 0; i < numberOfDays; i++ {
		rand.Shuffle(len(shuffledDays), func(i, j int) { shuffledDays[i], shuffledDays[j] = shuffledDays[j], shuffledDays[i] })
	}

	numberOfDaysPerPlayer := distributeDays(numberOfDays, numberOfPlayers)

	for i := 1; i <= numberOfPlayers; i++ {
		var playerName string
		fmt.Printf("Name of Player %d? ", i)
		_, _ = fmt.Scanln(&playerName)

		var d = numberOfDaysPerPlayer[i-1]
		player := NewPlayer(i, playerName, d)

		players = append(players, player)
	}

	g := Game{numberOfPlayers, numberOfDays, numberOfDaysPerPlayer, players, shuffledDays}

	return g
}

func distributeDays(numberOfDays int, numberOfPlayers int) []int {
	d := numberOfDays / numberOfPlayers
	r := numberOfDays % numberOfPlayers
	var dist []int

	for i := 0; i < numberOfPlayers; i++ {
		dist = append(dist, d)
	}

	for j := 0; j < r; j++ {
		dist[j] = dist[j] + 1
	}

	return dist
}

func (g *Game) ChooseDaysToOpen(p Printer) {
	rand.Seed(time.Now().UnixNano())

	for ok := true; ok; ok = len(g.ShuffledDays) > 0 {
		//1. Pick random player, weighted by number of pics remaining (?)
		var randomPlayerPosition = rand.Intn(len(g.Players)-1+1) + 1

		currentPlayer, err := getPlayer(g.Players, randomPlayerPosition)
		if err != nil {
			log.Fatal(err)
		}

		if currentPlayer.allDaysPicked() {
			//fmt.Printf("Done picking for %s\n", currentPlayer.name)
			continue
		}

		//2. "Player X, hit <enter> to stop the spinner" (have spinning icon)
		p.printRequestPlayerInteraction(currentPlayer.name)
		_, _ = fmt.Scanln()

		//3. On <enter>, get the next day from the shuffled list of days.
		var dayToOpen = g.ShuffledDays[0]
		p.printPlayerTurnResult(currentPlayer.name, dayToOpen)

		//4. Assign random number to Player X.
		currentPlayer.DaysToChoose = append(currentPlayer.DaysToChoose, dayToOpen)

		//5. Remove day just assigned to the user.
		g.ShuffledDays = g.ShuffledDays[1:]
		p.printDaysRemaining(len(g.ShuffledDays))
	}
}

func getPlayer(players []*Player, randomPlayerPosition int) (*Player, error) {
	for _, p := range players {
		if p.position == randomPlayerPosition {
			return p, nil
		}
	}

	return &Player{}, errors.New("no player found at " + strconv.Itoa(randomPlayerPosition))
}
