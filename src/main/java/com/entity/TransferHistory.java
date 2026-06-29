package com.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "transfer_histories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferHistory {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "request_id", nullable = false)
    private Long requestId;
    
    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    @Column(name = "result_status", nullable = false)
    private TransferHistoryResultStatus resultStatus;	
    
    @Column(name = "error_code")
    private String errorCode;	
    
    @Column(name = "error_message")
    private String errorMessage;	
    
    @Column(name = "balance_before", nullable = false, precision = 15, scale = 0)
    private BigDecimal balanceBefore;	
    
    @Column(name = "balance_after", nullable = false, precision = 15, scale = 0)
    private BigDecimal balanceAfter;	
	    	

}
