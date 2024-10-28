package com.social.network.exception;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ProfileExceptionalHandler extends ResponseEntityExceptionHandler
{

	private static final String ENUM_MSG = "values accepted for Enum class: ";


	// global exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest)
	{
		ErrorDetails errorDetails = null;
		if (exception instanceof ConstraintViolationException)
		{
			ConstraintViolationException cons = (ConstraintViolationException)exception;
			Set<String> errors = cons.getConstraintViolations().stream().map(n -> n.getMessage()).collect(Collectors.toSet());
			errorDetails = new ErrorDetails(new Date(), errors.toString(), webRequest.getDescription(false));
		}
		else
		{
			errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(), webRequest.getDescription(false));
		}
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request)
	{
		String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
		List<String> validationList = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> fieldError.getField() + " :: " + fieldError.getDefaultMessage()).collect(Collectors.toList());

		ErrorDetails dto = new ErrorDetails(new Date(), errorMessage, validationList.toString());

		return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(
	{AccessDeniedException.class, BadCredentialsException.class})
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(Exception ex, WebRequest request)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status,
		WebRequest request)
	{
		if (ex.getCause() instanceof InvalidFormatException) {
			boolean match = ex.getCause().getMessage().contains(ENUM_MSG);
			if (match) {
				int index = ex.getCause().getMessage().indexOf(ENUM_MSG);
				ErrorDetails error = ErrorDetails.builder().timestamp(new Date())
						.message(request.getDescription(false))
						.details(ex.getCause().getMessage().substring(index))
						.build();
				return new ResponseEntity<>(error, status);
			}
		}
		ErrorDetails error = ErrorDetails.builder().timestamp(new Date()).message(request.getDescription(false))
			.details(ex.getLocalizedMessage()).build();
		return new ResponseEntity<>(error, status);
	}

	@ExceptionHandler(BusinessValidationException.class)
	public ResponseEntity<ErrorDetails> handleInvalidEngine(RuntimeException e) {
		Throwable cause = e.getCause();
		return ResponseEntity
				.badRequest()
				.body(new ErrorDetails(
						new Date(),
						HttpStatus.BAD_REQUEST.getReasonPhrase(),
						cause.getMessage()
				));
	}
}
