package com.chilvers.henry.acer

/**
 * Advent Calendar Enjoyment Randomizer.
 * Copyright (c) 2017 - 2022.
 *
 * @author Henry Chilvers
 */
fun main(args: Array<String>) {
    try {
        val printer = Printer()
        val game = Game()

        printer.printOpeningScreen()

        game.setup(printer)

        printer.printPlayerTable(game)

        game.chooseDaysToOpen(printer)

        printer.printFinalResults(game)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}