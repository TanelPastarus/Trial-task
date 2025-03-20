package com.fujitsu.trialtask.service;


import com.fujitsu.trialtask.model.CityBaseFee;
import com.fujitsu.trialtask.model.ExtraFees;

public interface DeliveryService {
    double findDeliveryFee(String city, String vehicle);
    void updateCityBaseFee(CityBaseFee newCityBaseFee);
    void updateExtraFees(ExtraFees newExtraFees);
}
