package com.fujitsu.trialtask.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ZeroPositiveOrMinusOneValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// Custom annotation that is used to validate that a number is either 0, positive, or -1.
public @interface ZeroPositiveOrMinusOne {
    String message() default "Value must be 0, -1, or positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
