package com.chilvers.henry.acer;

/**
 * Advent Calendar Enjoyment Randomizer.
 * Copyright (c) 2017 - 2022.
 *
 * @author Henry Chilvers
 */
public class AcerApplication {
    public static void main(String[] args) {
        try {
            var printer = new Printer();
            var game = new Game();

            printer.printOpeningScreen();

            game.setup(printer);

            printer.printPlayerTable(game);


            game.chooseDaysToOpen(printer);

            printer.printFinalResults(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}