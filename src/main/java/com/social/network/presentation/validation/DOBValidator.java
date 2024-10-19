package com.social.network.presentation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DOBValidatorImpl.class)
@Target(
{ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DOBValidator
{

	String message() default "Invalid date format";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
