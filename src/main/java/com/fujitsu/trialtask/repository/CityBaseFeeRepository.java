package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.model.CityBaseFee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityBaseFeeRepository extends CrudRepository<CityBaseFee, Long> {
    CityBaseFee findCityBaseFeeByCity(City city);
}
