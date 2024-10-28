package com.social.network.presentation.validation;

import com.social.network.exception.BusinessValidationException;
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
		if(valueList.contains(countryDTO)){
			return true;
		}
		throw new BusinessValidationException(countryDTO, "Choose a valid country",countryDTO );

	}

	@Override
	public void initialize(CountryValidator constraintAnnotation)
	{
		valueList = Arrays.asList(CountryDTO.values());

	}
}
