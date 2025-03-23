package com.fujitsu.trialtask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadWeatherException.class)
    public ResponseEntity<String> handleForbiddenVehicle() {
        return new ResponseEntity<>("Usage of selected vehicle type is forbidden", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleVehicleOrCityNotFound() {
        return new ResponseEntity<>("No city or vehicle found", HttpStatus.NOT_FOUND);
    }

    // When values that are being updated are not correct (enum values are for example "tartu" not "TARTU")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleUpdateModelNotCorrect(HttpMessageNotReadableException h) {
        return new ResponseEntity<>(h.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // When the user tries to update the model with incorrect values
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = String.valueOf(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>("Values " + errorMessage, HttpStatus.BAD_REQUEST);
    }

    // When the user tries to use a method that is not allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException m) {
        return new ResponseEntity<>(m.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
