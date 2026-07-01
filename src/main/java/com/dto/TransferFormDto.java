package com.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransferFormDto {
	
	private String fromAccountNumber;
	private String toAccountNumber;
	private Long amount;
	private LocalDate transferDate;
	private String memo;
	

}
