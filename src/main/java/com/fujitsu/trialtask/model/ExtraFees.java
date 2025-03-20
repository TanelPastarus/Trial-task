package com.fujitsu.trialtask.model;

import com.fujitsu.trialtask.constraints.ZeroPositiveOrMinusOne;
import com.fujitsu.trialtask.enums.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExtraFees {
    @Id
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Vehicle vehicle;

    @NotNull
    @ZeroPositiveOrMinusOne
    private Double airTemperatureFeeUnderMinus10;

    @NotNull
    @ZeroPositiveOrMinusOne
    private Double airTemperatureFeeBetweenMinus10And0;

    @NotNull
    @ZeroPositiveOrMinusOne
    private Double windSpeedFeeBetween10And20;

    @NotNull
    @ZeroPositiveOrMinusOne
    private Double windSpeedFeeOver20;

    @NotNull
    @ZeroPositiveOrMinusOne
    private Double weatherPhenomenonFeeSnowy;

    @NotNull
    @ZeroPositiveOrMinusOne
    private Double weatherPhenomenonFeeRainy;

    @NotNull
    @ZeroPositiveOrMinusOne
    private Double weatherPhenomenonFeeGlaze;
}
