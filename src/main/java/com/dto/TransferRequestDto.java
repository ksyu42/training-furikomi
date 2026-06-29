package com.dto;

import lombok.*;
import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDto {

    /* 振込元口座番号：必須入力・半角数字7桁のみ */
    @NotBlank
    @Pattern(regexp = "\\d{7}", message = "口座番号は7桁の数字で入力してください")
    private String fromAccountNumber;

    /* 振込先口座番号：必須入力・半角数字7桁のみ */
    @NotBlank
    @Pattern(regexp = "\\d{7}", message = "口座番号は7桁の数字で入力してください")
    private String toAccountNumber;

    /* 振込金額：必須入力・1以上
     * ここにフィールドと Validation アノテーションを追加すること */
    @NotBlank
    @Min(1)
    private Long amount;

    /* 振込予定日：必須入力・本日以降の日付
     * ここにフィールドと Validation アノテーションを追加すること */
    @NotBlank
    @FutureOrPresent
    private LocalDate transferDate;

    /* 振込メモ：任意入力・200文字以内 */
    @Size(max = 200)
    private String memo;
}
