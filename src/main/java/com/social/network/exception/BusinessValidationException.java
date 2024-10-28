package com.social.network.exception;

import com.social.network.presentation.CountryDTO;
import jakarta.validation.ConstraintDeclarationException;

public class BusinessValidationException extends ConstraintDeclarationException {

    private CountryDTO validationErrorDto;

    public BusinessValidationException(CountryDTO validationErrorDto) {
        super();
        this.validationErrorDto = validationErrorDto;
    }

    public BusinessValidationException(CountryDTO validationErrorDto, String message, Object... args) {
        super(String.format(message, args));
        this.validationErrorDto = validationErrorDto;
    }
}