package com.chilvers.henry.acer

import java.io.IOException
import java.time.LocalDateTime

class Game {
    private val gameTime = LocalDateTime.now()
    private var numberOfPlayers = 0
    private var numberOfDays = 0
    private var shuffledDays = mutableListOf<Int>()
    var players = mutableListOf<Player>()

    @Throws(IOException::class)
    fun setup(printer: Printer) {
        printer.printNumberOfPlayersPrompt()
        numberOfPlayers = readLine()!!.toInt()

        printer.printNumberOfDaysPrompt()
        numberOfDays = readLine()!!.toInt()

        var shuffledDays = (1..numberOfDays).toMutableList().shuffled()

        for (r in 1 until numberOfDays) {
            shuffledDays = shuffledDays.shuffled()
        }

        this.shuffledDays = shuffledDays.toMutableList()

        setupPlayers(printer)
        println()
    }

    @Throws(IOException::class)
    fun chooseDaysToOpen(printer: Printer) {
        while (stillHasAvailableDaysToPick()) {
            //1. Pick random player, weighted by number of pics remaining (?)
            val randomPlayerPosition = (1..this.numberOfPlayers).random()
            val currentPlayer = getPlayer(randomPlayerPosition)

            if (currentPlayer.daysToChoose.size == getNumberOfDaysPerPlayer()) {
                continue
            }

            //2. "Player X, hit <space bar> to stop the spinner" (have spinning icon)
            printer.printRequestPlayerInteraction(currentPlayer.name)

            //3. On <space bar>, get the next day from the shuffled list of days.
            readLine()

            val dayToOpen = this.shuffledDays[0]

            //4. Assign random number to Player X.
            printer.printPlayerTurnResult(currentPlayer.name, dayToOpen)

            currentPlayer.daysToChoose.add(dayToOpen)
            this.shuffledDays.removeFirst()

            if (this.shuffledDays.size == 0) {
            } else {
                printer.printDaysRemaining(this.shuffledDays.size)
            }
        }
    }

//    fun getLongestPlayersName(): Int {
//        return players.stream()
//            .map { it.name }
//            .sorted { p1, p2 -> p2.length.compareTo(p1.length) }
//            .findFirst()
//            .get()
//            .length
//    }

    private fun getNumberOfDaysPerPlayer(): Int {
        return numberOfDays / numberOfPlayers
    }

    private fun getPlayer(randomPlayerPosition: Int): Player {
        return players.stream()
            .filter { it.position == randomPlayerPosition }
            .findFirst()
            .get()
    }

    private fun stillHasAvailableDaysToPick(): Boolean {
        return players.stream()
            .mapToInt { it.daysToChoose.count() }
            .sum() != numberOfDays
    }

    @Throws(IOException::class)
    private fun setupPlayers(printer: Printer) {
        val players: MutableList<Player> = ArrayList()

        for (i in 1..this.numberOfPlayers) {
            printer.printPlayerNamePrompt(i)

            val playerName = readLine()!!
            val player = Player(i, playerName)

            players.add(player)
        }

        this.players = players
    }
}