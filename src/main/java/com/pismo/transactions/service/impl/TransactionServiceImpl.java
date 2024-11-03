package com.pismo.transactions.service.impl;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.service.AccountService;
import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import com.pismo.transactions.mapper.TransactionMapper;
import com.pismo.transactions.respository.TransactionRepository;
import com.pismo.transactions.service.TransactionService;
import com.pismo.transactions.validations.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionValidator transactionValidator;

    @Override
    public Long createTransactions(TransactionDto transactionDto) {

        AccountDto account = accountService.getAccount(transactionDto.getAccountId());

        // Account id exists validation -
        transactionValidator.validateTransactionAccount(transactionDto, account);
        //Validate if appropriate operation type exits
        transactionValidator.validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());
        // Operation Id exits validation & check for amount aswell
        // Check balance based on operation ID
        // If balance < 0 --  Reject
        // Atomically save transaction & account with new balance - Synchronized

        Transaction transaction = transactionMapper.dtoToEntity(transactionDto);
        transaction = transactionRepository.save(transaction);
        transactionDto.setTransactionId(transaction.getTransactionId());
        return transactionDto.getTransactionId();
    }

    @Override
    public TransactionDto getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).get();
        return transactionMapper.entityToDto(transaction);
    }
}
