package com.pismo.transactions.service.impl;

import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import com.pismo.transactions.mapper.TransactionMapper;
import com.pismo.transactions.respository.TransactionRepository;
import com.pismo.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;
    @Override
    public TransactionDto createTransactions(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.dtoToEntity(transactionDto);
        transaction = transactionRepository.save(transaction);
        transactionDto.setTransactionId(transaction.getTransactionId());
        return transactionDto;
    }

    @Override
    public TransactionDto getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).get();
        return transactionMapper.entityToDto(transaction);
    }
}
