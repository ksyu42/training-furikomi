package com.entity;

/**
 * 口座状態 Enum
 * ACTIVE  : 通常利用可能
 * FROZEN  : 凍結中（出金不可）
 * CLOSED  : 閉鎖済み（入出金不可）
 */
public enum AccountStatus {

   ACTIVE,
   FROZEN,
   CLOSED;

}
