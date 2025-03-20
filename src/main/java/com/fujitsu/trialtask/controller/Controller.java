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

    @GetMapping("/")
    public ResponseEntity<String> defaultPage() {
        return new ResponseEntity<>("""
                OK
                """, HttpStatus.OK);

    }

    @GetMapping("/fee/{city}/{vehicle}")
    public ResponseEntity<Double> getDeliveryFee(@PathVariable String city, @PathVariable String vehicle) {
        double fee = deliveryService.findDeliveryFee(city, vehicle);
        return new ResponseEntity<>(fee, HttpStatus.OK);
    }

    @PutMapping("/basefee/update")
    public ResponseEntity<String> updateCityBaseFee(@Valid @RequestBody CityBaseFee newCityBaseFee) {
        deliveryService.updateCityBaseFee(newCityBaseFee);
        return new ResponseEntity<>("Basefees for city " + newCityBaseFee.getCity() + " updated succesfully", HttpStatus.OK);
    }

    @PutMapping("/extrafee/update")
    public ResponseEntity<String> updateVehicleExtraFee(@Valid @RequestBody ExtraFees newExtraFees) {
        deliveryService.updateExtraFees(newExtraFees);
        return new ResponseEntity<>("Extrafees for vehicle " + newExtraFees.getVehicle() + " updated succesfully", HttpStatus.OK);
    }

}
