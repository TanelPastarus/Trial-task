package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.enums.Vehicle;
import com.fujitsu.trialtask.enums.WeatherPhenomenon;
import com.fujitsu.trialtask.model.CityBaseFee;
import com.fujitsu.trialtask.model.ExtraFees;
import com.fujitsu.trialtask.model.Weather;
import com.fujitsu.trialtask.repository.CityBaseFeeRepository;
import com.fujitsu.trialtask.repository.ExtraFeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryServiceImplTest {

    @Mock
    private ExtraFeeRepository extraFeesRepository;

    @Mock
    private CityBaseFeeRepository cityBaseFeeRepository;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    private CityBaseFee cityBaseFee;

    private ExtraFees extraFees;

    private Weather weather;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityBaseFee = new CityBaseFee(1L, City.TARTU, 3.5, 3.0, 2.5);
        extraFees = new ExtraFees(1L, Vehicle.BIKE, 1.0,
                0.5, 0.5, -1.0,
                1.0, 0.5, -1.0);
        weather = new Weather(1L, City.TARTU, 20.0, 10.0, WeatherPhenomenon.SNOWY, 123456, Timestamp.from(Instant.now()));
    }

    @Test
    void findDeliveryFee_returnsCorrectFee() {
        String city = "TARTU";
        String vehicle = "BIKE";

        when(cityBaseFeeRepository.findCityBaseFeeByCity(City.valueOf(city))).thenReturn(cityBaseFee);
        when(weatherService.findLatestWeatherByCity(City.valueOf(city))).thenReturn(weather);
        when(extraFeesRepository.findExtraFeesByVehicle(Vehicle.valueOf(vehicle))).thenReturn(extraFees);

        double fee = deliveryService.findDeliveryFee(city, vehicle);

        assertEquals(5.0, fee);
    }

    @Test
    void updateCityBaseFee() {
        when(cityBaseFeeRepository.findCityBaseFeeByCity(City.TARTU)).thenReturn(cityBaseFee);

        CityBaseFee newCityBaseFee = new CityBaseFee(1L, City.TARTU, 4.5, 4.0, 3.5);

        deliveryService.updateCityBaseFee(newCityBaseFee);

        verify(cityBaseFeeRepository, times(1)).save(cityBaseFee);

        assertEquals(4.5, cityBaseFee.getBikeFee());
        assertEquals(4.0, cityBaseFee.getScooterFee());
        assertEquals(3.5, cityBaseFee.getCarFee());
    }

    @Test
    void updateExtraFees_updatesCorrectly_callsRepositorySave() {
        when(extraFeesRepository.findExtraFeesByVehicle(Vehicle.BIKE)).thenReturn(extraFees);

        ExtraFees newExtraFees = new ExtraFees(1L, Vehicle.BIKE, 1.0,
                2.0, 3.0, 4.0,
                5.0, 6.0, 7.0);

        deliveryService.updateExtraFees(newExtraFees);

        verify(extraFeesRepository, times(1)).save(extraFees);

        assertEquals(1.0, extraFees.getAirTemperatureFeeUnderMinus10());
        assertEquals(2.0, extraFees.getAirTemperatureFeeBetweenMinus10And0());
        assertEquals(3.0, extraFees.getWindSpeedFeeBetween10And20());
        assertEquals(4.0, extraFees.getWindSpeedFeeOver20());
        assertEquals(5.0, extraFees.getWeatherPhenomenonFeeSnowy());
        assertEquals(6.0, extraFees.getWeatherPhenomenonFeeRainy());
        assertEquals(7.0, extraFees.getWeatherPhenomenonFeeGlaze());
    }
}