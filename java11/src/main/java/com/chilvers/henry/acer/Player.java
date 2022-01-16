package com.chilvers.henry.acer;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Player {
    private int position;
    private String name;
    private List<Integer> daysToChoose;

    public String finalResults() {
        return this.getDaysToChoose().stream()
         .sorted()
         .map(Object::toString)
         .collect(Collectors.joining(","));
    }
}