package com.fujitsu.trialtask.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
// Each enum has a list of keywords associated with that phenomenon
public enum WeatherPhenomenon {
    SNOWY(new ArrayList<>(List.of("snow", "sleet"))),
    RAINY(new ArrayList<>(List.of("rain", "shower"))),
    GLAZE(new ArrayList<>(List.of("glaze", "hail", "thunder"))),
    NONE(new ArrayList<>());

    private final List<String> keywordsRelatedToPhenomenon;
}
