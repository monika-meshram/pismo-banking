package com.pismo.transactions.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Operations that can be used while creating a Transaction
 * {@link #NORMAL_PURCHASE}
 * {@link #PURCHASE_WITH_INSTALLMENTS}
 * {@link #WITHDRAWAL}
 * {@link #CREDIT_VOUCHER}
 */
@Getter
@RequiredArgsConstructor
public enum OperationType {
    /**
     * Is registered with Negative Amount
     */
    NORMAL_PURCHASE(1, "Normal Purchase"),
    /**
     * Is registered with Negative Amount
     */
    PURCHASE_WITH_INSTALLMENTS(2, "Purchase with installments"),
    /**
     * Is registered with Negative Amount
     */
    WITHDRAWAL(3, "Withdrawal"),
    /**
     * Is registered with Positive Amount
     */
    CREDIT_VOUCHER(4, "Credit Voucher");

    private final int id;
    private final String description;

    /**
     * Creating a list of Operation Types which are registered with Negative Amount
     */
    public static final List<OperationType> DEBIT_OPERATION_TYPES = List.of(NORMAL_PURCHASE, PURCHASE_WITH_INSTALLMENTS, WITHDRAWAL);

    /**
     * Creating a list of Operation Types which are registered with Positive Amount
     */
    public static final List<OperationType> CREDIT_OPERATION_TYPES = List.of(CREDIT_VOUCHER);

    /*public static boolean isValidOperationType(int operationTypeId){
        return Arrays.stream(OperationType.values())
                .filter(value -> value.id == operationTypeId).findFirst().isPresent();
        //return Arrays.stream(OperationType.values()).findAny();
    }*/

    /**
     * Checks if the Operation Type provided is Debit Operation Type
     * @param operationTypeId
     * @return True/False based on the filter result
     */
    public static boolean isDebitOperationType(int operationTypeId){
        return DEBIT_OPERATION_TYPES.stream().filter(value -> value.id == operationTypeId).findFirst().isPresent();
    }

    /**
     * Checks if the Operation Type provided is Credit Operation Type
     * @param operationTypeId
     * @return True/False based on the filter result
     */
    public static boolean isCreditOperationType(int operationTypeId){
        return CREDIT_OPERATION_TYPES.stream().filter(value -> value.id == operationTypeId).findFirst().isPresent();
    }
}
