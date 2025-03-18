package com.fujitsu.trialtask.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
public class WeatherDataImport {
    private final WeatherService weatherService;

    public WeatherDataImport(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @Scheduled(cron = "${weather.cronjob}")
    public void importData() throws SAXException, IOException, ParserConfigurationException {
        weatherService.updateWeatherData();
    }

}
