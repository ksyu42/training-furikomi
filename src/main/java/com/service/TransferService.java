package com.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.TransferRequestDto;
import com.dto.TransferResponseDto;
import com.entity.Account;
import com.entity.AccountStatus;
import com.entity.TransferHistory;
import com.entity.TransferHistoryResultStatus;
import com.entity.TransferRequest;
import com.entity.TransferRequestStatus;
import com.entity.TransferResponseDtoStatus;
import com.repository.AccountRepository;
import com.repository.TransferHistoryRepository;
import com.repository.TransferRequestRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository        accountRepository;
    private final TransferRequestRepository transferRequestRepository;
    private final TransferHistoryRepository transferHistoryRepository;

    /* ここに @Transactional を付与すること */
    @Transactional(rollbackFor = Exception.class)
    public TransferResponseDto execute(TransferRequestDto dto) {

        /* ① 同一口座チェック
         *    fromAccountNumber と toAccountNumber が等しい場合
         *    IllegalArgumentException をスローすること                   */
    	if(dto.getFromAccountNumber().equals(dto.getToAccountNumber())) {
            throw new IllegalArgumentException("振込元と振込先が同一です");
        }

        /* ② 振込元口座取得
         *    accountRepository.findByAccountNumber() で検索
         *    存在しない場合は IllegalArgumentException をスローすること   */
    	Optional<Account> fromAccountInfo = accountRepository.findByAccountNumber(dto.getFromAccountNumber());
    	if(fromAccountInfo.isEmpty()) {
    		throw new IllegalArgumentException("振込元口座が見つかりません");
    	}
    	//振込元 存在する場合は、Accountで格納
    	Account fromAccount = fromAccountInfo.orElseThrow();
    	
        /* ③ 振込元口座の状態チェック
         *    ACTIVE でない場合は IllegalStateException をスローすること   */
    	if(AccountStatus.ACTIVE != fromAccount.getStatus()) {
            throw new IllegalStateException ("振込元口座が利用できません");
    	}

        /* ④ 残高チェック
         *    compareTo を使い、balance < amount の場合
         *    IllegalStateException をスローすること                       */
    	//出金額、        valueOf()：LONG　→　BigDecimal型変換 
    	BigDecimal dtoAmount = BigDecimal.valueOf(dto.getAmount());
    	//compareTo() →左辺が「小さい：-1」、「同じ：0」、「大きい：1」 を返す
    	if(dtoAmount.compareTo(fromAccount.getBalance()) > 0) {
            throw new IllegalStateException ("残高が不足しています");
    	}

        /* ⑤ 振込先口座取得・状態チェック
         *    存在しない場合 → IllegalArgumentException
         *    CLOSED の場合 → IllegalStateException                       */
    	Optional<Account> toAccountInfo = accountRepository.findByAccountNumber(dto.getToAccountNumber());
    	if(toAccountInfo.isEmpty()) {
    		throw new IllegalArgumentException("振込先口座が見つかりません");
    	}
    	//振込先 存在する場合は、Accountで格納
    	Account toAccount = toAccountInfo.orElseThrow();
    	if(AccountStatus.CLOSED == toAccount.getStatus()) {
            throw new IllegalStateException ("振込先口座は閉鎖されています");
    	}


        /* ⑥ 出金処理
         *    BigDecimal balanceBefore = fromAccount.getBalance() で退避
         *    subtract した値を set して accountRepository.save()          */
    	BigDecimal balanceBefore = fromAccount.getBalance();
    	//振込元のBlanceにセット →[元のBalance - 出金]
    	fromAccount.setBalance(balanceBefore.subtract(dtoAmount)); 
    	accountRepository.save(fromAccount);
    	BigDecimal balanceAfter = fromAccount.getBalance();   	

        /* ⑦ 入金処理
         *    add した値を set して accountRepository.save()               */
    	//振込先のBlanceにセット →[元のBalance + 入金]
    	toAccount.setBalance(toAccount.getBalance().add(dtoAmount));
    	accountRepository.save(toAccount);

        /* ⑧ 振込依頼レコード登録
         *    TransferRequest を builder で生成して save()                 */
    	TransferRequest request = new TransferRequest();
        request.setFromAccountId(fromAccount.getAccountId());
        request.setAmount(dto.getAmount());
        request.setTransferDate(dto.getTransferDate());
        request.setStatus(TransferRequestStatus.COMPLETED);
        request.setRequestedAt(LocalDateTime.now());
        transferRequestRepository.save(request);
    	
        /* ⑨ 振込履歴レコード登録
         *    TransferHistory を builder で生成して save()                 */
    	TransferHistory history = new TransferHistory();
        history.setRequestId(request.getRequestId()); //TransferRequest requestに紐づけ
        history.setProcessedAt(LocalDateTime.now());
        history.setResultStatus(TransferHistoryResultStatus.COMPLETED);
        history.setBalanceBefore(balanceBefore);
        history.setBalanceAfter(balanceAfter);
        transferHistoryRepository.save(history);

        /* ⑩ TransferResponseDto を builder で生成して return する            */
        return TransferResponseDto.builder()
        		.requestId(request.getRequestId())
		        .status(TransferResponseDtoStatus.COMPLETED) 
		        .balanceAfter(balanceAfter.longValue()) 
		        .processedAt(LocalDateTime.now()) 
		        .build();
        
//         null; // 実装後に削除
    }
}
