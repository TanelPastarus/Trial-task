package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.exceptions.BadWeatherException;
import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.enums.Vehicle;
import com.fujitsu.trialtask.enums.WeatherPhenomenon;
import com.fujitsu.trialtask.model.CityBaseFee;
import com.fujitsu.trialtask.model.ExtraFees;
import com.fujitsu.trialtask.model.Weather;
import com.fujitsu.trialtask.repository.CityBaseFeeRepository;
import com.fujitsu.trialtask.repository.ExtraFeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final ExtraFeeRepository extraFeesRepository;
    private final WeatherService weatherService;
    private final CityBaseFeeRepository cityBaseFeeRepository;

    @Override
    public double findDeliveryFee(String vehicle, String city) {
        City c = City.valueOf(city.toUpperCase());
        Vehicle v = Vehicle.valueOf(vehicle.toUpperCase());

        Weather w = weatherService.findLatestWeatherByCity(c);
        ExtraFees e = extraFeesRepository.findVehicleExtraFeesByVehicle(v);
        CityBaseFee cbf = cityBaseFeeRepository.findCityBaseFeeByCity(c);

        double extrafee = 0;
        double basefee = 0;

        if (w != null) extrafee = calculateWeatherExtraFees(e, w);

        switch (v) {
            case Vehicle.BIKE -> basefee = cbf.getBikeFee();
            case Vehicle.CAR -> basefee = cbf.getCarFee();
            case Vehicle.SCOOTER -> basefee = cbf.getScooterFee();
        }

        return basefee + extrafee;
    }

    private double calculateWeatherExtraFees(ExtraFees e, Weather w) {
        Double windSpeedFee;
        Double airTemperatureFee;
        Double weatherPhenomenonFee;

        double airTemperature = w.getAirTemperature();
        double windSpeed = w.getWindSpeed();
        WeatherPhenomenon weatherPhenomenon = w.getWeatherPhenomenon();

        windSpeedFee = calculateWindSpeedFee(e, windSpeed);
        airTemperatureFee = calculateAirTemperatureFee(e, airTemperature);
        weatherPhenomenonFee = calculateWeatherPhenomenonFee(e, weatherPhenomenon);

        if (weatherPhenomenonFee == -1 || windSpeedFee == -1 || airTemperatureFee == -1) throw new BadWeatherException();

        return airTemperatureFee + windSpeedFee + airTemperatureFee;
    }

    private Double calculateWeatherPhenomenonFee(ExtraFees e, WeatherPhenomenon weatherPhenomenon) {
        if (weatherPhenomenon == WeatherPhenomenon.SNOWY) {
            return e.getWeatherPhenomenonFeeSnowy();
        }
        if (weatherPhenomenon == WeatherPhenomenon.RAINY) {
            return e.getWeatherPhenomenonFeeRainy();
        }
        if (weatherPhenomenon == WeatherPhenomenon.GLAZE) {
            return e.getWeatherPhenomenonFeeGlaze();
        }
        return 0.0;
    }

    private Double calculateAirTemperatureFee(ExtraFees e, double airTemperature) {
        if (airTemperature < -10) {
            return e.getAirTemperatureFeeUnderMinus10();
        }
        if (airTemperature <= 0) {
            return e.getAirTemperatureFeeBetweenMinus10And0();
        }
        return 0.0;
    }

    private Double calculateWindSpeedFee(ExtraFees e, double windSpeed) {
        if (windSpeed > 20) {
            return e.getWindSpeedFeeOver20();
        }
        if (windSpeed >= 10) {
            return e.getWindSpeedFeeBetween10And20();
        }
        return 0.0;
    }
}
