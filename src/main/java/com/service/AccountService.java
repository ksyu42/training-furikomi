package com.service;

import com.entity.Account;
import com.entity.AccountStatus;
import com.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * 口座番号から口座情報を取得する。
     * 存在しない場合は空の Optional を返す。
     */
    public Optional<Account> findByAccountNumber(String accountNumber) {
        /* ① accountRepository から口座番号で検索して返す */
    	Optional<Account> accountInfo = accountRepository.findByAccountNumber(accountNumber);

    	return accountInfo;
    	//return Optional.empty(); // 実装後に削除
    	
    }

    /**
     * 口座番号が存在するかどうかを確認する。
     */
    public boolean existsByAccountNumber(String accountNumber) {
        /* ② accountRepository で存在確認して返す */
    	boolean accountNumCheck = accountRepository.existsByAccountNumber(accountNumber);
    	
    	return accountNumCheck;
    	
        //return false; // 実装後に削除
    }

    /**
     * 口座番号から口座情報を取得する。
     * 存在しない場合は IllegalArgumentException をスローする。
     */
    public Account getByAccountNumber(String accountNumber) {
        /* ③ accountRepository で検索し、存在しない場合は
         *    IllegalArgumentException をスローすること
         *    （メッセージ例：「口座が見つかりません: 」+ accountNumber）
         */
    	Optional<Account> accountInfo = accountRepository.findByAccountNumber(accountNumber);
    	if(accountInfo.isEmpty()) {
    		throw new IllegalArgumentException("口座が見つかりません: "+ accountNumber);
    	}
    	
    	return accountInfo.get();
    	
       //return null; // 実装後に削除
    }
}