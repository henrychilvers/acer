package com.chilvers.henry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Advent Calendar Enjoyment Randomizer.
 * Copyright (c) 2017.
 *
 * @author Henry Chilvers
 */
public class AcerApplication {
    private static final String WELCOME = "Welcome to...";
    private static final String APP_NAME = "A.C.E.R.";
    private static final String RESULTS = "Results...";
    private static final String NUMBER_OF_PLAYERS_PROMPT = "Number of players? ";
    private static final String NUMBER_OF_DAYS_PROMPT = "Number of days in calendar? ";
    private static final String READY_PROMPT = "Ready? Here we go... ";
    private static final String PRINT_CALENDAR_PROMPT = "Print results?";

    public static void main(String[] args) {
        try {
            ASCIIArtGenerator artGen = new ASCIIArtGenerator();

            printWelcome();
            printLogo(artGen);

            Game game = setupGame();
            printReady();

            Thread.sleep(500);
            clearScreen();

            printLogo(artGen);
            printPlayerTable(game);
            chooseDaysToOpen(game);

            clearScreen();
            printLogo(artGen);
            printResults();
            printPlayerTable(game);

            printPrintOutRequest(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void chooseDaysToOpen(Game game) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        while (game.stillHasAvailableDaysToPick()) {
            //1. Pick random player, weighted by number of pics remaining (?)
            int randomPlayerPosition = rnd.nextInt(1, game.getNumberOfPlayers() + 1);
            Player currentPlayer = game.getPlayers().stream().filter(p -> p.getPosition() == randomPlayerPosition).findFirst().get();

            if (currentPlayer.getDaysToChoose().size() == game.getNumberOfDaysPerPlayer()) {
                continue;
            }

            //2. "Player X, hit <space bar> to stop the spinner" (have spinning icon)
            System.out.println(currentPlayer.getName() + ", hit <enter> to pick a number");

            //3. On <space bar>, get the next day from the shuffled list of days.
            reader.readLine();
            //if (input.equals(" ")) {
                int dayToOpen = game.getShuffledDays().get(0);

                //4. Assign random number to Player X.
                System.out.println(currentPlayer.getName() + ", you got day " + dayToOpen);
                System.out.println();
                currentPlayer.getDaysToChoose().add(dayToOpen);

                game.getShuffledDays().remove(0);// .remove(dayToOpen);
                //System.console().writer().write(game.getShuffledDays().toString() + "\n");
                System.out.println("[Days remaining: " + game.getShuffledDays().size() + "]");
                //Update Player Table...
            //} else {
//                for (String phase : phases) {
//                    System.console().writer().print("\b" + phase);
//                    Thread.sleep(100);
//                }
            //}
        }

//        while (true)
//        {
//            for (String phase : phases)
//            {
//                System.out.print("\b" + phase);
//                Thread.sleep(100);
//            }
//        }
    }

    //https://stackoverflow.com/questions/20058366/shuffle-a-list-of-integers-with-java-8-streams-api
    private static final Collector<?, ?, ?> SHUFFLER = Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                Collections.shuffle(list);
                return list;
            }
    );

    @SuppressWarnings("unchecked")
    private static <T> Collector<T, ?, List<T>> toShuffledList() {
        return (Collector<T, ?, List<T>>) SHUFFLER;
    }

    private static Game setupGame() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(NUMBER_OF_PLAYERS_PROMPT);
        int numberOfPlayers = Integer.parseInt(reader.readLine());
        System.out.println(NUMBER_OF_DAYS_PROMPT);
        int numberOfDays = Integer.parseInt(reader.readLine());

        List<Integer> shuffledDays = IntStream.rangeClosed(1, numberOfDays)
                .boxed()
                .collect(toShuffledList());
        //System.console().writer().write(shuffledDays.toString() + "\n");

        for (int r = 1; r < numberOfDays; r++) {
            shuffledDays = shuffledDays.stream().collect(toShuffledList());
            //System.out.write(shuffledDays.toString() + "\n");
        }

        Game game = Game.builder()
                .numberOfPlayers(numberOfPlayers)
                .numberOfDays(numberOfDays)
                .shuffledDays(shuffledDays)
                .build();

        setupPlayers(game);

        return game;
    }

    private static void setupPlayers(Game game) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Player> players = new ArrayList<>();

        for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
            System.out.println("Name of Player " + i + "? ");
            String playerName = reader.readLine();

            Player player = Player.builder()
                    .position(i)
                    .name(playerName)
                    .daysToChoose(new ArrayList<>(game.getNumberOfDaysPerPlayer()))
                    .build();

            players.add(player);
        }

        game.setPlayers(players);
    }

    private static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    private static void printPrintOutRequest(Game game) {
        //System.out.println(PRINT_CALENDAR_PROMPT);
    }

    private static void printWelcome() {
        System.out.println(WELCOME);
    }

    private static void printResults() {
        System.out.println(RESULTS);
    }

    private static void printLogo(ASCIIArtGenerator artGen) throws Exception {
        System.out.println();
        artGen.printTextArt(APP_NAME, ASCIIArtGenerator.ART_SIZE_MEDIUM);
        System.out.println("( Advent           Calendar          Enjoyment        Randomizer )");
        System.out.println();
    }

    private static void printReady() {
        System.out.println(READY_PROMPT);
    }

    private static void printPlayerTable(Game game) {
        //console.writer().println(Strings.repeat("\\u2500", 50));
        //console.writer().println(StringUtils.repeat("\u2500",50));
        String horizontalSeparator = new String(new char[40]).replace('\0', '\u2500');

        System.out.printf("\u250C%s\u2510\n", horizontalSeparator);
        System.out.printf("\u2502%-10s|%20s         \u2502\n", "Player", " Days to Open");
        System.out.printf("\u250C%s\u2510\n", horizontalSeparator);

        game.getPlayers()
                .forEach(p -> System.out
                        .printf("%s%-10s| %s           %s\n",
                                "\u2502",
                                p.getName(),
                                p.getDaysToChoose().stream().sorted().map(Object::toString).collect(Collectors.joining(",")),
                                "\u2502"));
        System.out.println("\u2514" + horizontalSeparator + "\u2518");
    }
}