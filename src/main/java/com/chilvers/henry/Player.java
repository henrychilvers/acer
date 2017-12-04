package com.chilvers.henry;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Player {
    private int position;
    private String name;
    private List<Integer> daysToChoose;
}
