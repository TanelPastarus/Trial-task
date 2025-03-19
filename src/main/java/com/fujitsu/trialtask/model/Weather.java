package com.fujitsu.trialtask.model;

import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.enums.WeatherPhenomenon;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private City name;

    // Maximum and minimum highest temperature ever recorded
    @Max(value = 60)
    @Min(value = -90)
    @NotNull
    private Double airTemperature;

    // Maximum surface wind
    @PositiveOrZero
    @Max(value = 115)
    @NotNull
    private Double windSpeed;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WeatherPhenomenon weatherPhenomenon;

    @NotNull
    private Integer WMOCode;

    @CreationTimestamp
    private Timestamp timestamp;
}
