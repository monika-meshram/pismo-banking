package com.pismo.transactions.service;

import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;

public interface TransactionService {
    public TransactionDto createTransactions(TransactionDto transactionDto);
    public TransactionDto getTransaction(Long transactionId);
}
