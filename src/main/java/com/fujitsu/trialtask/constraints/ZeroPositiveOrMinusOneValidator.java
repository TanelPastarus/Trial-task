package com.fujitsu.trialtask.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ZeroPositiveOrMinusOneValidator implements ConstraintValidator<ZeroPositiveOrMinusOne, Double> {

    @Override
    public void initialize(ZeroPositiveOrMinusOne constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {

        // NotNull decorator should catch the null values, but just in case added a check.
        if (value == null) {
            return false;
        }

        return value == -1 || value >= 0;
    }
}
