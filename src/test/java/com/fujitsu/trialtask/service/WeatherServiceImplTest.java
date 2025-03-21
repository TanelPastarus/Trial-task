package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.enums.WeatherPhenomenon;
import com.fujitsu.trialtask.model.Weather;
import com.fujitsu.trialtask.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    private Weather weather;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherServiceImpl(weatherRepository);
        weather = new Weather(1L, City.TARTU, 20.0, 10.0, WeatherPhenomenon.SNOWY, 123456, Timestamp.from(Instant.now()));
    }

    @Test
    void testSaveWeather() {
        weatherService.saveWeather(weather);
        verify(weatherRepository, times(1)).save(weather);
    }

    @Test
    void findLatestWeatherByCity_returnsLatestWeather() {
        when(weatherRepository.findTop1WeatherByNameOrderByTimestampDesc(City.TARTU)).thenReturn(weather);

        Weather actualWeather = weatherService.findLatestWeatherByCity(City.TARTU);

        assertEquals(weather, actualWeather);
        verify(weatherRepository, times(1)).findTop1WeatherByNameOrderByTimestampDesc(City.TARTU);
    }

}