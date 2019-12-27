package com.chilvers.henry;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
public class Game {
    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    private LocalDateTime gameTime = LocalDateTime.now();
    private int numberOfPlayers;
    private int numberOfDays;
    private List<Player> players;
    private List<Integer> shuffledDays;

    public int getNumberOfDaysPerPlayer() {
        return numberOfDays / numberOfPlayers;
    }

    public int getLongestPlayersName() {
        return players
                .stream()
                .map(Player::getName)
                .sorted((x, y) -> Integer.compare(y.length(), x.length()))
                .findFirst()
                .get()
                .length();
    }

    public Boolean stillHasAvailableDaysToPick() {
        return players
                .stream()
                .mapToInt(p -> p.getDaysToChoose().size())
                .sum() != numberOfDays;
    }

    public List<Integer> numbersAlreadyTaken() {
        return players
                .stream()
                .flatMap(p -> p.getDaysToChoose().stream())
                .collect(Collectors.toList());
    }
}