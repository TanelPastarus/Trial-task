package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.model.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {
    Weather findTop1WeatherByNameOrderByTimestampDesc(City city);
}
