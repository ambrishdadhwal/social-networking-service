package com.social.network.presentation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidateLoginImpl.class)
@ReportAsSingleViolation
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateLogin
{

	String message() default "Provide email or username , not both";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
