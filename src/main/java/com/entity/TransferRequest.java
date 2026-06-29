package com.entity;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate; // 自動作成日用
import org.springframework.data.jpa.domain.support.AuditingEntityListener; // Auditing用

import jakarta.persistence.*;


@Entity
@Table(name = "transfer_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "from_account_id", nullable = false)
    private Long fromAccountId;

    @Column(nullable = false, precision = 15, scale = 0)
    private Long amount;

    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @Column
    private String memo;

    @Column(nullable = false)
    private String status;	

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;	
	

}
