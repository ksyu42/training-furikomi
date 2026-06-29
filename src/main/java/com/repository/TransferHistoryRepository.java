package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.TransferHistory;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {


}
