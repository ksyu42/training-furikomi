package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.TransferRequest;

public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long>{
	
	

}
