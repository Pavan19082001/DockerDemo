package com.example.exception;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllException(Exception exp, WebRequest request) {

		ErrorDetails er = new ErrorDetails();
		er.setTimestamp(LocalDate.now());
		er.setMessage(exp.getMessage());
		er.setDetails(request.getDescription(false));
		er.setHttpCodeMessage("Internal Server Error");

		logger.error(exp.getMessage());

		return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleManagerNotFoundException(UserNotFoundException exp,
			WebRequest request) {

		ErrorDetails er = new ErrorDetails();
		er.setTimestamp(LocalDate.now());
		er.setMessage(exp.getMessage());
		er.setDetails(request.getDescription(false));
		er.setHttpCodeMessage("Not Found");
		logger.error(exp.getMessage());
		return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		StringBuilder details = new StringBuilder();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			details.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(". ");
		}
		ErrorDetails er = new ErrorDetails();
		er.setTimestamp(LocalDate.now());
		er.setMessage("Validation fails");
		er.setDetails(details.toString());
		er.setHttpCodeMessage("Bad Request");
		logger.error("Validation fails");
		return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
	}
}
