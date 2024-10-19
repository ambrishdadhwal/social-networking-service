package com.social.network.presentation.validation;

import com.social.network.presentation.CountryDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CountryValidatorImpl implements ConstraintValidator<CountryValidator, CountryDTO>
{

	List<CountryDTO> valueList = null;

	@Override
	public boolean isValid(CountryDTO countryDTO, ConstraintValidatorContext context)
	{
		return valueList.contains(countryDTO);
	}

	@Override
	public void initialize(CountryValidator constraintAnnotation)
	{
		valueList = Arrays.asList(CountryDTO.values());

	}
}
