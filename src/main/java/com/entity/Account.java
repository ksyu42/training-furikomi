package com.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

/*===============================
 * ① Auditing を有効にするアノテーションを追加すること
 * ② 必要な import を追加すること
 *===============================*/
@Entity
@EntityListeners(AuditingEntityListener.class) // ★Auditingの監視をON
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true, length = 7)
    private String accountNumber;

    @Column(nullable = false, length = 100)
    private String accountHolder;

    @Column(nullable = false, precision = 15, scale = 0)
    private BigDecimal balance;

    @Column(nullable = false, length = 3)
    private String branchCode;

    /*===============================
     * ② status フィールドを追加すること
     *   - 型 : AccountStatus（Enum）
     *   - DB : 文字列として保存する
     *   - 制約: NOT NULL
     *===============================*/
    
    private AccountStatus status;

    /*===============================
     * ③ createdAt フィールドを追加すること
     *   - 型 : LocalDateTime
     *   - 作成時に自動セット・更新不可
     *===============================*/

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    

    /*===============================
     * ④ updatedAt フィールドを追加すること
     *   - 型 : LocalDateTime
     *   - 更新のたびに自動セット
     *===============================*/
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
