package com.pismo.transactions.service;

import com.pismo.transactions.dto.TransactionDto;

public interface TransactionService {
    public Long createTransactions(TransactionDto transactionDto);
    //public TransactionDto getTransaction(Long transactionId);
}
