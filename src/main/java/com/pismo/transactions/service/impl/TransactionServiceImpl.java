package com.pismo.transactions.service.impl;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.service.AccountService;
import com.pismo.exceptions.TransactionNotFoundException;
import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import com.pismo.transactions.mapper.TransactionMapper;
import com.pismo.transactions.respository.TransactionRepository;
import com.pismo.transactions.service.TransactionService;
import com.pismo.transactions.validations.TransactionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionValidator transactionValidator;

    /**
     * A transaction is created and associated with their respective account.
     * This method creates a transaction and updates the balance in Account table, in case of any issue while saving the transaction, the balance in account will be rolled back.
     * Pessimistic Locking is used ensures data consistency by preventing concurrent modifications that could lead to inconsistencies.
     * @param transactionDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    public Long createTransactions(TransactionDto transactionDto) {
        AccountDto accountDto = accountService.getAccount(transactionDto.getAccountId());

        // Account id exists validation -
        //transactionValidator.validateTransactionAccount(transactionDto, accountDto);

        // Validate if appropriate operation type exits
        transactionValidator.validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());

        // If balance < 0 --  Reject
        transactionValidator.validateAccountBalanceForTransaction(transactionDto, accountDto);

        // Atomically save transaction & account with new balance - Synchronized
        // Save account
        accountDto.setBalance(accountDto.getBalance().add(transactionDto.getAmount()));
        accountService.createAccount(accountDto);

        // Save transaction
        Transaction transaction = TransactionMapper.dtoToEntity(transactionDto);
        transaction = transactionRepository.save(transaction);
        transactionDto.setTransactionId(transaction.getTransactionId());

        return transactionDto.getTransactionId();
    }

    @Override
    public TransactionDto getTransaction(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if(transaction.isPresent()){
            return TransactionMapper.entityToDto(transaction.get());
        } else {
            // We can throw an Exception here, to let user know that accound with ID doesnot exits.
            // OR without letting the user know that exact cause, just log it, we can return just the empty response(Security purpose)
            logger.error("Transaction with ID : " + transactionId + " does not exits.");
            throw new TransactionNotFoundException("Transaction with ID : " + transactionId + " does not exits");
        }
    }
}
