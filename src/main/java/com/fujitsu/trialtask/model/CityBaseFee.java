package com.fujitsu.trialtask.model;

import com.fujitsu.trialtask.enums.City;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CityBaseFee {
    @Id
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private City city;

    @PositiveOrZero
    @NotNull
    private Double bikeFee;

    @PositiveOrZero
    @NotNull
    private Double scooterFee;

    @PositiveOrZero
    @NotNull
    private Double carFee;
}
