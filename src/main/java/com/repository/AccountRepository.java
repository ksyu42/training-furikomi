package com.repository;

import com.entity.Account;
import com.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface AccountRepository extends JpaRepository<Account, Long> {

    /* ① 口座番号で1件検索するメソッドを追加する（戻り値: Optional） */
	Optional<Account> findByAccountNumber(String accountNumber);

    /* ② 口座状態で絞り込むメソッドを追加する（戻り値: List）     */
	List<Account> finbByStatus(String status);

    /* ③ 口座番号の存在確認メソッドを追加する（戻り値: boolean）           */
	boolean existsByAccountNumber(String accountNumber);

}