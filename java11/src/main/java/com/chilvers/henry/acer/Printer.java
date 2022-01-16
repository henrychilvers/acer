package com.chilvers.henry.acer;

import de.vandermeer.asciitable.AT_Cell;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.util.LinkedList;

import static com.chilvers.henry.acer.ASCIIArtFontSize.ART_SIZE_MEDIUM;

public class Printer {
    private static final String WELCOME = "Welcome to...";
    private static final String APP_NAME = "A.C.E.R.";
    private static final String NUMBER_OF_PLAYERS_PROMPT = "Number of players? ";
    private static final String NUMBER_OF_DAYS_PROMPT = "Number of days in calendar? ";
    private static final String RESULTS = "Results...";
    private static final String READY_PROMPT = "Ready? Here we go... ";
    private static final String FAREWELL = "Thanks for using A.C.E.R.! Enjoy your advent calendar!";

    private final ASCIIArtGenerator artGen = new ASCIIArtGenerator();

    public void printOpeningScreen() {
        clearScreen();

        printWelcome();
        printLogo();
        printReady();

        printLogo();

    }

    public void printFinalResults(Game game) {
        clearScreen();
        printLogo();
        printResults();
        printPlayerTable(game);
        printFarewell();
    }

    public void clearScreen() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    public void printNumberOfPlayersPrompt() {
        System.out.print(NUMBER_OF_PLAYERS_PROMPT);
    }

    public void printNumberOfDaysPrompt() {
        System.out.print(NUMBER_OF_DAYS_PROMPT);
    }

    public void printPlayerNamePrompt(Integer i) {
        System.out.printf("Name of Player %d? ", i);
    }

    public void printPlayerTurnResult(String playerName, Integer dayToOpen) {
        System.out.println(playerName + ", you got day " + dayToOpen);
        System.out.println();
    }

    void printPlayerTable(Game game) {
        var at = new AsciiTable();
        var cwc = new CWC_LongestLine();

        at.addRule();
        var headerCells = at.addRow("Player", "Days to Open").getCells();
        at.addRule();
        setCellPadding(headerCells);
        headerCells.get(1).getContext().setTextAlignment(TextAlignment.CENTER);

        game.getPlayers().forEach(it -> {
            var cells = at.addRow(it.getName(), it.finalResults()).getCells();
            setCellPadding(cells);
        });
        at.addRule();

        at.getRenderer().setCWC(cwc);
        System.out.println(at.render());
    }

    private void setCellPadding(LinkedList<AT_Cell> cells) {
        cells.get(0).getContext().setPaddingLeftChar(' ');
        cells.get(0).getContext().setPaddingRightChar(' ');
        cells.get(0).getContext().setPaddingLeft(1);
        cells.get(0).getContext().setPaddingRight(1);

        cells.get(1).getContext().setPaddingLeftChar(' ');
        cells.get(1).getContext().setPaddingRightChar(' ');
        cells.get(1).getContext().setPaddingLeft(1);
        cells.get(1).getContext().setPaddingRight(1);
    }

    private void printFarewell() {
        System.out.println();
        System.out.println(FAREWELL);
    }

    private void printWelcome() {
        System.out.println(WELCOME);
    }

    private void printResults() {
        System.out.println(RESULTS);
    }

    private void printLogo() {
        System.out.println();
        artGen.printTextArt(APP_NAME, ART_SIZE_MEDIUM);
        System.out.println("( Advent           Calendar          Enjoyment        Randomizer )");
        System.out.println();
    }

    public void printDaysRemaining(Integer daysRemaining) {
        System.out.println("[Days remaining: " + daysRemaining + "]");
    }

    public void printRequestPlayerInteraction(String currentPlayerName) {
        System.out.println(currentPlayerName + ", hit <enter> to pick a number...");
    }

    private void printReady() {
        System.out.println(READY_PROMPT);
    }
}