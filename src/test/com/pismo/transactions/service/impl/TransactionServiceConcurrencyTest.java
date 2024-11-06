package com.pismo.transactions.service.impl;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.service.AccountService;
import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import com.pismo.transactions.respository.TransactionRepository;
import com.pismo.transactions.service.TransactionService;
import com.pismo.transactions.validations.TransactionValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class TransactionServiceConcurrencyTest {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @MockBean
    private TransactionValidator transactionValidator;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void init() {
        transactionRepository.deleteAll();
    }

    @Test
    void testConcurrentRuns_createTransaction() {
        AccountDto accountDto = AccountDto.builder().documentNumber("12345").balance(BigDecimal.valueOf(0L, 2)).build();
        TransactionDto transactionDto = TransactionDto.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();
        Transaction transaction = Transaction.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();

        accountService.createAccount(accountDto);
        Mockito.doNothing().when(transactionValidator).validateTransactionAccount(transactionDto, accountDto);
        Mockito.doNothing().when(transactionValidator).validateAccountBalanceForTransaction(transactionDto, accountDto);
        Mockito.doNothing().when(transactionValidator).validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());

        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                transactionService.createTransactions(transactionDto);
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        AccountDto savedAccountDto = accountService.getAccount(1L);
        Assert.assertNotNull(savedAccountDto);
        Assert.assertEquals(BigDecimal.valueOf(1000L, 2), savedAccountDto.getBalance());
    }


}

