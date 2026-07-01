package com.controller;

import com.dto.*;
import com.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/api/transfer")
    public ResponseEntity<?> transfer(
            @RequestBody @Valid TransferRequestDto dto,
            BindingResult result) {

        /* ① バリデーションエラーがある場合は 400 Bad Request を返すこと */
    	if(result.hasErrors()) {
    		return ResponseEntity.badRequest().body("入力内容に不備があります。");
    	}

        /* ② try-catch で transferService.execute(dto) を呼び出すこと
         *    IllegalArgumentException → 400
         *    IllegalStateException   → 400
         *    Exception（その他）     → 500                              */
    	try {
    		transferService.execute(dto);
    		return ResponseEntity.ok().build();
    		
    	}catch(IllegalArgumentException | IllegalStateException e){
    		log.warn(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
    		
    		
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("予期せぬエラーが発生しました。");
    	}

//        return ResponseEntity.ok().build(); // 実装後に修正すること
    }
}
