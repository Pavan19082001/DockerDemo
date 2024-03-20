package com.example.exception;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ErrorDetails {
	
	private LocalDate timestamp;
	
	private String message;
	
	private String details;
	
	private String httpCodeMessage;

}
