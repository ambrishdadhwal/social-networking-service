package com.social.network.presentation.validation;

import com.social.network.presentation.ProfileLoginDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateLoginImpl implements ConstraintValidator<ValidateLogin, ProfileLoginDTO>
{

	@Override
	public boolean isValid(ProfileLoginDTO value, ConstraintValidatorContext context)
	{
		if (!(value instanceof ProfileLoginDTO))
		{
			throw new IllegalArgumentException("@ValidateLogin only applies to SocialLoginDTO objects");
		}

		String email = value.getEmail();
		String userName = value.getUserName();

		if ((email != null) && (userName != null))
		{
			return false;
		}

		return true;
	}

	@Override
	public void initialize(ValidateLogin constraintAnnotation)
	{

	}

}
