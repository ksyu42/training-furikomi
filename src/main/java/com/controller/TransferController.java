package com.controller;

import com.dto.*;
import com.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/api/transfer")
    public ResponseEntity<?> transfer(
            @RequestBody @Valid TransferRequestDto dto,
            BindingResult result) {

        /* ① バリデーションエラーがある場合は 400 Bad Request を返すこと */

        /* ② try-catch で transferService.execute(dto) を呼び出すこと
         *    IllegalArgumentException → 400
         *    IllegalStateException   → 400
         *    Exception（その他）     → 500                              */

        return ResponseEntity.ok().build(); // 実装後に修正すること
    }
}
