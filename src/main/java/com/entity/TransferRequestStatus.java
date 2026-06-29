package com.entity;

/**
 * 処理ステータス Enum
 * PENDING   : 通常利用可能
 * COMPLETED   : 凍結中（出金不可）
 * FAILED  : 閉鎖済み（入出金不可）
 */
public enum TransferRequestStatus {
	
	PENDING,
	COMPLETED,
	FAILED;

}
