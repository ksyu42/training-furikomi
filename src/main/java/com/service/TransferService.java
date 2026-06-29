package com.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.TransferRequestDto;
import com.dto.TransferResponseDto;
import com.entity.Account;
import com.entity.TransferRequest;
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
    @Transactional
    public TransferResponseDto execute(TransferRequestDto dto) {

        /* ① 同一口座チェック
         *    fromAccountNumber と toAccountNumber が等しい場合
         *    IllegalArgumentException をスローすること                   */
    	if(dto.getFromAccountNumber().equals(dto.getToAccountNumber())) {
            throw new IllegalArgumentException("振込元口座と振込先口座を同じにすることはできません");
        }

        /* ② 振込元口座取得
         *    accountRepository.findByAccountNumber() で検索
         *    存在しない場合は IllegalArgumentException をスローすること   */
    	Optional<Account> accountInfo = accountRepository.findByAccountNumber(dto.getFromAccountNumber());
    	if(accountInfo.isEmpty()) {
    		throw new IllegalArgumentException("口座番号は存在しません");
    	}

        /* ③ 振込元口座の状態チェック
         *    ACTIVE でない場合は IllegalStateException をスローすること   */
    	

        /* ④ 残高チェック
         *    compareTo を使い、balance < amount の場合
         *    IllegalStateException をスローすること                       */

        /* ⑤ 振込先口座取得・状態チェック
         *    存在しない場合 → IllegalArgumentException
         *    CLOSED の場合 → IllegalStateException                       */

        /* ⑥ 出金処理
         *    BigDecimal balanceBefore = fromAccount.getBalance() で退避
         *    subtract した値を set して accountRepository.save()          */

        /* ⑦ 入金処理
         *    add した値を set して accountRepository.save()               */

        /* ⑧ 振込依頼レコード登録
         *    TransferRequest を builder で生成して save()                 */

        /* ⑨ 振込履歴レコード登録
         *    TransferHistory を builder で生成して save()                 */

        /* ⑩ TransferResponseDto を builder で生成して return する            */
        return null; // 実装後に削除
    }
}
