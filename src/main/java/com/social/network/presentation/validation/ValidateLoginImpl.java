package com.social.network.presentation.validation;

import com.social.network.presentation.UserProfileLoginDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateLoginImpl implements ConstraintValidator<ValidateLogin, UserProfileLoginDTO>
{

	@Override
	public boolean isValid(UserProfileLoginDTO value, ConstraintValidatorContext context)
	{
		if (!(value instanceof UserProfileLoginDTO))
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
