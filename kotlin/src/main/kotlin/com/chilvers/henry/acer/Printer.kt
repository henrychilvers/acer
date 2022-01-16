package com.chilvers.henry.acer

import de.vandermeer.asciitable.AT_Cell
import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import java.util.*

class Printer {
    private val artGen: ASCIIArtGenerator = ASCIIArtGenerator()

    @Throws(InterruptedException::class)
    fun printOpeningScreen() {
        clearScreen()

        printWelcome()
        printLogo()
        printReady()
        printLogo()
    }

    fun printFinalResults(game: Game) {
        clearScreen()
        printLogo()
        printResults()
        printPlayerTable(game)
        printFarewell()
    }

    private fun clearScreen() {
        try {
            val os = System.getProperty("os.name")

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls")
            } else {
                Runtime.getRuntime().exec("clear")
            }
        } catch (e: Exception) {
            //  Handle any exceptions.
        }
    }

    fun printNumberOfPlayersPrompt() {
        print(NUMBER_OF_PLAYERS_PROMPT)
    }

    fun printNumberOfDaysPrompt() {
        print(NUMBER_OF_DAYS_PROMPT)
    }

    fun printPlayerNamePrompt(i: Int?) {
        System.out.printf("Name of Player %d? ", i)
    }

    fun printPlayerTurnResult(playerName: String, dayToOpen: Int) {
        println("$playerName, you got day $dayToOpen")
        println()
    }

    fun printPlayerTable(game: Game) {
//        val longestName = max(game.getLongestPlayersName(), "Player".length) + 1
        val at = AsciiTable()
        val cwc = CWC_LongestLine()

        at.addRule()
        val headerCells = at.addRow("Player", "Days to Open").cells
        at.addRule()
        setCellPadding(headerCells)
        headerCells[1].context.textAlignment = TextAlignment.CENTER

        game.players.forEach {
            val cells = at.addRow(it.name, it.finalResults()).cells
            setCellPadding(cells)
        }
        at.addRule()

        at.renderer.cwc = cwc
        println(at.render()) //todo: Calculate max width??
    }

    fun printDaysRemaining(daysRemaining: Int) {
        println("[Days remaining: $daysRemaining]")
    }

    fun printRequestPlayerInteraction(currentPlayerName: String) {
        println("$currentPlayerName, hit <enter> to pick a number...")
    }

    private fun printFarewell() {
        println()
        println(FAREWELL)
    }

    private fun printWelcome() {
        println(WELCOME)
    }

    private fun printResults() {
        println(RESULTS)
    }

    private fun printLogo() {
        println()
        artGen.printTextArt(APP_NAME, ASCIIArtFontSize.ART_SIZE_MEDIUM)
        println("( Advent           Calendar          Enjoyment        Randomizer )")
        println()
    }

    private fun printReady() {
        println(READY_PROMPT)
    }

    private fun setCellPadding(cells: LinkedList<AT_Cell>) {
        cells[0].context.paddingLeftChar = ' '
        cells[0].context.paddingRightChar = ' '
        cells[0].context.paddingLeft = 1
        cells[0].context.paddingRight = 1

        cells[1].context.paddingLeftChar = ' '
        cells[1].context.paddingRightChar = ' '
        cells[1].context.paddingLeft = 1
        cells[1].context.paddingRight = 1
    }

    companion object {
        private const val WELCOME = "Welcome to..."
        private const val APP_NAME = "A.C.E.R."
        private const val NUMBER_OF_PLAYERS_PROMPT = "Number of players? "
        private const val NUMBER_OF_DAYS_PROMPT = "Number of days in calendar? "
        private const val RESULTS = "Results..."
        private const val READY_PROMPT = "Ready? Here we go... "
        private const val FAREWELL = "Thanks for using A.C.E.R.! Enjoy your advent calendar!"
    }
}