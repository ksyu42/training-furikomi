package com.dto;

import lombok.*;
import java.time.LocalDateTime;

import com.entity.TransferResponseDtoStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {
    private Long                             requestId;
    private TransferResponseDtoStatus        status;
    private Long          					 balanceAfter;
    private LocalDateTime                    processedAt;
    private String        					 errorCode;
    private String        					 errorMessage;
}
