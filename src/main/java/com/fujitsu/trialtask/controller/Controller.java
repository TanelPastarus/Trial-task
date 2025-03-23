package com.fujitsu.trialtask.controller;

import com.fujitsu.trialtask.model.CityBaseFee;
import com.fujitsu.trialtask.model.ExtraFees;
import com.fujitsu.trialtask.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class Controller {
    private final DeliveryService deliveryService;

    /**
     * Default page
     *
     * @return - default page
     */
    @GetMapping("/")
    public ResponseEntity<String> defaultPage() {
        return new ResponseEntity<>("""
                Delivery API:  /fee/{city}/{vehicle} <br>
                Update a city: /basefee/update <br>
                Update extrafees: /extrafee/update <br>
                Supported cities: TARTU, TALLINN, PÄRNU <br>
                Supported vehicles: Car, Bike, Scooter
                """, HttpStatus.OK);

    }

    /**
     * Get the delivery fee for a city and vehicle
     *
     * @param city    - city name
     * @param vehicle - vehicle type
     * @return - delivery fee
     */
    @GetMapping("/fee/{city}/{vehicle}")
    public ResponseEntity<Double> getDeliveryFee(@PathVariable String city, @PathVariable String vehicle) {
        double fee = deliveryService.findDeliveryFee(city, vehicle);
        return new ResponseEntity<>(fee, HttpStatus.OK);
    }

    /**
     * Update the base fee for a city
     *
     * @param newCityBaseFee - CityBaseFee object
     */
    @PutMapping("/basefee/update")
    public ResponseEntity<String> updateCityBaseFee(@Valid @RequestBody CityBaseFee newCityBaseFee) {
        deliveryService.updateCityBaseFee(newCityBaseFee);
        return new ResponseEntity<>("Basefees for city " + newCityBaseFee.getCity() + " updated succesfully", HttpStatus.OK);
    }

    /**
     * Update the extra fee for a vehicle
     *
     * @param newExtraFees - Extrafees object
     */
    @PutMapping("/extrafee/update")
    public ResponseEntity<String> updateVehicleExtraFee(@Valid @RequestBody ExtraFees newExtraFees) {
        deliveryService.updateExtraFees(newExtraFees);
        return new ResponseEntity<>("Extrafees for vehicle " + newExtraFees.getVehicle() + " updated succesfully", HttpStatus.OK);
    }

}
