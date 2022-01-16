package com.chilvers.henry.acer;

import lombok.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@ToString
public class Game {
    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    private LocalDateTime gameTime = LocalDateTime.now();
    private int numberOfPlayers;
    private int numberOfDays;
    private List<Player> players;
    private List<Integer> shuffledDays;

    //https://stackoverflow.com/questions/20058366/shuffle-a-list-of-integers-with-java-8-streams-api
    private static final Collector<?, ?, ?> SHUFFLER = Collectors.collectingAndThen(Collectors.toList(),
                                                                                    list -> {
        Collections.shuffle(list);

        return list;
    });

    @SuppressWarnings("unchecked")
    private static <T> Collector<T, ?, List<T>> toShuffledList() {
        return (Collector<T, ?, List<T>>) SHUFFLER;
    }

    public int getNumberOfDaysPerPlayer() {
        return numberOfDays / numberOfPlayers;
    }

    public Player getPlayer(Integer randomPlayerPosition) {
        return this.players.stream().filter(p -> p.getPosition() == randomPlayerPosition).findFirst().get();
    }

    public int getLongestPlayersName() {
        return players.stream()
                      .map(Player::getName)
                      .sorted((x, y) -> Integer.compare(y.length(), x.length()))
                      .findFirst()
                      .get()
                      .length();
    }

    public Boolean stillHasAvailableDaysToPick() {
        return players.stream().mapToInt(p -> p.getDaysToChoose().size()).sum() != numberOfDays;
    }

    public void setup(Printer printer) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        printer.printNumberOfPlayersPrompt();
        this.numberOfPlayers = Integer.parseInt(reader.readLine());

        printer.printNumberOfDaysPrompt();
        this.numberOfDays = Integer.parseInt(reader.readLine());

        var shuffledDays = IntStream.rangeClosed(1, numberOfDays).boxed().collect(toShuffledList());
        //System.console().writer().write(shuffledDays.toString() + "\n");

        for (var r = 1; r < numberOfDays; r++) {
            shuffledDays = shuffledDays.stream().collect(toShuffledList());
        }

        this.shuffledDays = shuffledDays;

        setupPlayers(printer);
    }

    private void setupPlayers(Printer printer) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        List<Player> players = new ArrayList<>();

        for (var i = 1; i <= this.getNumberOfPlayers(); i++) {
            printer.printPlayerNamePrompt(i);

            var playerName = reader.readLine();
            var player = new Player(i, playerName, new ArrayList<>(this.getNumberOfDaysPerPlayer()));

            players.add(player);
        }

        this.setPlayers(players);
    }

    public void chooseDaysToOpen(Printer printer) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var rnd = ThreadLocalRandom.current();

        while (this.stillHasAvailableDaysToPick()) {
            //1. Pick random player, weighted by number of pics remaining (?)
            var randomPlayerPosition = rnd.nextInt(1, this.getNumberOfPlayers() + 1);
            var currentPlayer = this.getPlayer(randomPlayerPosition);

            if (currentPlayer.getDaysToChoose().size() == this.getNumberOfDaysPerPlayer()) {
                continue;
            }

            //2. "Player X, hit <space bar> to stop the spinner" (have spinning icon)
            printer.printRequestPlayerInteraction(currentPlayer.getName());

            //3. On <space bar>, get the next day from the shuffled list of days.
            reader.readLine();

            int dayToOpen = this.getShuffledDays().get(0);

            //4. Assign random number to Player X.
            printer.printPlayerTurnResult(currentPlayer.getName(), dayToOpen);

            currentPlayer.getDaysToChoose().add(dayToOpen);

            this.getShuffledDays().remove(0);
            printer.printDaysRemaining(this.getShuffledDays().size());
        }
    }
}