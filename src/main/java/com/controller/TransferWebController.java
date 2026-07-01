package com.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.dto.*;
import com.entity.Account;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.repository.AccountRepository;
import com.service.TransferService;

@Controller
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferWebController {

    private final TransferService  transferService;
    private final AccountRepository accountRepository;

    @GetMapping("/input")
    public String showInput(Model model) {
        /* 空の TransferFormDto を "form" として Model に追加して "transfer/input" を返す */
    	model.addAttribute("form", new TransferFormDto());
        return "transfer/input";
    }

    @PostMapping("/confirm")
    public String confirm(
            @Valid @ModelAttribute("form") TransferFormDto form,
            BindingResult result,
            Model model) {

        /* ① バリデーションエラーがあれば "transfer/input" を返す */
    	if(result.hasErrors()) {
    		return "transfer/input";
    	}

        /* ② 振込元口座を検索し、存在しない場合は rejectValue して "transfer/input" を返す */
    	Optional<Account> fromAccount = accountRepository.findByAccountNumber(form.getFromAccountNumber());
    	if(fromAccount.isEmpty()) {
    		result.rejectValue("fromAccountNumber", "error.notfound", "口座が見つかりません");
    		return "transfer/input";
    	}

        /* ③ 振込先口座を検索し、存在しない場合は rejectValue して "transfer/input" を返す */
    	Optional<Account> toAccount = accountRepository.findByAccountNumber(form.getToAccountNumber());
    	if(toAccount.isEmpty()) {
    		result.rejectValue("toAccountNumber", "error.notfound", "口座が見つかりません");
    		return "transfer/input"; 
    	}

        /* ④ fromAccount, toAccount, form を Model に追加して "transfer/confirm" を返す */
    	model.addAttribute("form", form);
    	model.addAttribute("fromAccount", fromAccount);
    	model.addAttribute("toAccount", toAccount);
        return "transfer/confirm"; // 実装後に修正すること
    }

    @PostMapping("/execute")
    public String execute(
            @ModelAttribute TransferFormDto form,
            RedirectAttributes ra) {

        /* try-catch で transferService.execute() を呼び出す
         * 成功 → FlashAttribute に response をセットして complete/{requestId} にリダイレクト
         * 失敗 → FlashAttribute に errorMessage をセットして input にリダイレクト         */
    	try {
    		TransferRequestDto requestDto = new TransferRequestDto();
            requestDto.setFromAccountNumber(form.getFromAccountNumber());
            requestDto.setToAccountNumber(form.getToAccountNumber());
            requestDto.setAmount(form.getAmount());
            requestDto.setTransferDate(form.getTransferDate());
            requestDto.setMemo(form.getMemo());
            
            // ransferService.execute() を呼び出si
            TransferResponseDto responseDto = transferService.execute(requestDto);
            // TransferRequestDtoをformから生成してサービスを呼び出す
            Long requestId = responseDto.getRequestId();

            // 成功した場合：レスポンスをFlashAttributeにセットしてリダイレクト
            ra.addFlashAttribute("response", requestId); 
            
            return "redirect:/transfer/complete/" + requestId;

        } catch (Exception e) {
            // 3. 失敗した場合：エラーメッセージをFlashAttributeにセットして入力画面へ
            ra.addFlashAttribute("errorMessage", "振込処理中にエラーが発生しました: " + e.getMessage());
            
            return "redirect:/transfer/input";
        }
    }

    @GetMapping("/complete/{requestId}")
    public String complete(@PathVariable Long requestId, Model model) {
        /* requestId を Model に追加して "transfer/complete" を返す */
    	model.addAttribute("requestId", requestId);
        return "transfer/complete";
    }
}