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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private static final double NO_EXTRA_FEE = 0.0;
    private static final int VEHICLE_IS_FORBIDDEN_VALUE = -1;

    private final ExtraFeeRepository extraFeesRepository;
    private final WeatherService weatherService;
    private final CityBaseFeeRepository cityBaseFeeRepository;

    @Override
    public double findDeliveryFee(String city, String vehicle) {
        City c = City.valueOf(city.toUpperCase());
        Vehicle v = Vehicle.valueOf(vehicle.toUpperCase());

        Weather w = weatherService.findLatestWeatherByCity(c);
        ExtraFees e = extraFeesRepository.findExtraFeesByVehicle(v);
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

    @Override
    public void updateCityBaseFee(CityBaseFee newCityBaseFee) {
        City c = newCityBaseFee.getCity();

        CityBaseFee cbf = cityBaseFeeRepository.findCityBaseFeeByCity(c);

        cbf.setBikeFee(newCityBaseFee.getBikeFee());
        cbf.setCarFee(newCityBaseFee.getCarFee());
        cbf.setScooterFee(newCityBaseFee.getScooterFee());

        cityBaseFeeRepository.save(cbf);
    }

    @Override
    public void updateExtraFees(ExtraFees newExtraFees) {
        Vehicle v = newExtraFees.getVehicle();

        ExtraFees e = extraFeesRepository.findExtraFeesByVehicle(v);

        e.setAirTemperatureFeeUnderMinus10(newExtraFees.getAirTemperatureFeeUnderMinus10());
        e.setAirTemperatureFeeBetweenMinus10And0(newExtraFees.getAirTemperatureFeeBetweenMinus10And0());

        e.setWeatherPhenomenonFeeRainy(newExtraFees.getWeatherPhenomenonFeeRainy());
        e.setWeatherPhenomenonFeeGlaze(newExtraFees.getWeatherPhenomenonFeeGlaze());
        e.setWeatherPhenomenonFeeSnowy(newExtraFees.getWeatherPhenomenonFeeSnowy());

        e.setWindSpeedFeeOver20(newExtraFees.getWindSpeedFeeOver20());
        e.setWindSpeedFeeBetween10And20(newExtraFees.getWindSpeedFeeBetween10And20());

        extraFeesRepository.save(e);
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

        if (weatherPhenomenonFee == VEHICLE_IS_FORBIDDEN_VALUE ||
                windSpeedFee == VEHICLE_IS_FORBIDDEN_VALUE ||
                airTemperatureFee == VEHICLE_IS_FORBIDDEN_VALUE)
            throw new BadWeatherException();

        return airTemperatureFee + windSpeedFee + weatherPhenomenonFee;
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
        return NO_EXTRA_FEE;
    }

    private Double calculateAirTemperatureFee(ExtraFees e, double airTemperature) {
        if (airTemperature < -10) {
            return e.getAirTemperatureFeeUnderMinus10();
        }
        if (airTemperature <= 0) {
            return e.getAirTemperatureFeeBetweenMinus10And0();
        }
        return NO_EXTRA_FEE;
    }

    private Double calculateWindSpeedFee(ExtraFees e, double windSpeed) {
        if (windSpeed > 20) {
            return e.getWindSpeedFeeOver20();
        }
        if (windSpeed >= 10) {
            return e.getWindSpeedFeeBetween10And20();
        }
        return NO_EXTRA_FEE;
    }
}
