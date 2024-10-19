package com.social.network.presentation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DOBValidatorImpl implements ConstraintValidator<DOBValidator, LocalDate>
{

	private static final String DATE_PATTERN = "MM/dd/yyyy";

	@Override
	public void initialize(DOBValidator customDate)
	{
		System.out.println("here...");
	}

	@Override
	public boolean isValid(LocalDate customDateField, ConstraintValidatorContext cxt)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		try
		{
			sdf.setLenient(false);
			sdf.parse(customDateField.toString());
			return true;
		}
		catch (ParseException e)
		{
			return false;
		}
	}

}