package com.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {
    private Long          requestId;
    private String        status;
    private Long          balanceAfter;
    private LocalDateTime processedAt;
    private String        errorCode;
    private String        errorMessage;
}
