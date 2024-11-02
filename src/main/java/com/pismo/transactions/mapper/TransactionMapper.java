package com.pismo.transactions.mapper;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction dtoToEntity(TransactionDto transactionDto){
        return Transaction.builder()
                .accountId(transactionDto.getAccountId())
                .operationsTypeId(transactionDto.getOperationsTypeId())
                .amount(transactionDto.getAmount())
                .build();
    }

    public TransactionDto entityToDto(Transaction transaction){
        return TransactionDto.builder()
                .accountId(transaction.getAccountId())
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .eventDate(transaction.getEventDate())
                .operationsTypeId(transaction.getOperationsTypeId())
                .build();
    }
}
