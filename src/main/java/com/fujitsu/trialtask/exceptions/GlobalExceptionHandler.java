package com.fujitsu.trialtask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadWeatherException.class})
    public ResponseEntity<String> handleForbiddenVehicle() {
        return new ResponseEntity<>("Usage of selected vehicle type is forbidden", HttpStatus.FORBIDDEN);
    }
}
