package com.fujitsu.trialtask.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum City {
    TARTU("Tartu-Tõravere"),
    TALLINN("Tallinn-Harku"),
    PÄRNU("Pärnu");

    private final String stationName;
}