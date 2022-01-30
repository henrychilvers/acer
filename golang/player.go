package main

import (
	"fmt"
	"sort"
	"strings"
)

type Player struct {
	position     int
	name         string
	numberOfDays int `default:"0"`
	DaysToChoose []int
}

func NewPlayer(playerPosition int, playerName string, numberOfDays int) *Player {
	return &Player{playerPosition, playerName, numberOfDays, []int{}}
}

func (p Player) allDaysPicked() bool {
	return len(p.DaysToChoose) == p.numberOfDays
}

func (p Player) finalResults() string {
	if len(p.DaysToChoose) == 0 {
		return " <Stay tuned...> "
	}

	sort.Ints(p.DaysToChoose)
	return strings.Trim(strings.Replace(fmt.Sprint(p.DaysToChoose), " ", ",", -1), "[]")
}
