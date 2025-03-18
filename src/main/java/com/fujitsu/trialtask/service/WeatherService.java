package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.model.Weather;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface WeatherService {
    void saveWeather(Weather weather);
    Weather findLatestWeatherByCity(City city);
    void updateWeatherData() throws ParserConfigurationException, IOException, SAXException;
}
