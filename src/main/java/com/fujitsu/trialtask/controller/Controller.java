package com.fujitsu.trialtask.controller;

import com.fujitsu.trialtask.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
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
        double fee = deliveryService.findDeliveryFee(vehicle, city);
        return new ResponseEntity<>(fee, HttpStatus.OK);
    }

}
