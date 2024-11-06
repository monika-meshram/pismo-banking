package com.pismo.transactions.mapper;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class TransactionMapper {
    public static Transaction dtoToEntity(TransactionDto transactionDto){
        return Transaction.builder()
                .accountId(transactionDto.getAccountId())
                .operationsTypeId(transactionDto.getOperationsTypeId())
                .amount(transactionDto.getAmount())
                .build();
    }

    public static TransactionDto entityToDto(Transaction transaction){
        return TransactionDto.builder()
                .accountId(transaction.getAccountId())
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .eventDate(transaction.getEventDate())
                .operationsTypeId(transaction.getOperationsTypeId())
                .build();
    }
}
