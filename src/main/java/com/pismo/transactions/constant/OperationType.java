package com.pismo.transactions.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum OperationType {
    NORMAL_PURCHASE(1, "Normal Purchase"),
    PURCHASE_WITH_INSTALLMENTS(2, "Purchase with installments"),
    WITHDRAWAL(3, "Withdrawal"),
    CREDIT_VOUCHER(4, "Credit Voucher");
    private final int id;
    private final String description;

    public static final List<OperationType> DEBIT_OPERATION_TYPES = List.of(NORMAL_PURCHASE, PURCHASE_WITH_INSTALLMENTS, WITHDRAWAL);
    public static final List<OperationType> CREDIT_OPERATION_TYPES = List.of(CREDIT_VOUCHER);

    /*public static boolean isValidOperationType(int operationTypeId){
        return Arrays.stream(OperationType.values())
                .filter(value -> value.id == operationTypeId).findFirst().isPresent();
        //return Arrays.stream(OperationType.values()).findAny();
    }*/

    public static boolean isDebitOperationType(int operationTypeId){
        return DEBIT_OPERATION_TYPES.stream().filter(value -> value.id == operationTypeId).findFirst().isPresent();
    }

    public static boolean isCreditOperationType(int operationTypeId){
        return CREDIT_OPERATION_TYPES.stream().filter(value -> value.id == operationTypeId).findFirst().isPresent();
    }
}
