package com.pismo.transactions.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperationType {
    NORMAL_PURCHASE(1, "Normal Purchase"),
    PURCHASE_WITH_INSTALLMENTS(2, "Purchase with installments"),
    WITHDRAWAL(3, "Withdrawal"),
    CREDIT_VOUCHER(4, "Credit Voucher");
    private final int id;
    private final String description;


}
