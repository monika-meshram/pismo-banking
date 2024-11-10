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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class TransactionServiceAtomicTransactionTest {
    @Autowired
    private TransactionService transactionService;

    @MockBean
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
    void testRollback_createTransaction() {
        AccountDto accountDto = AccountDto.builder().documentNumber("12345").balance(BigDecimal.valueOf(0L, 2)).build();
        TransactionDto transactionDto = TransactionDto.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();
        Transaction transaction = Transaction.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();

        Long accountId = accountService.createAccount(accountDto);
        Mockito.doNothing().when(transactionValidator).validateAccountBalanceForTransaction(transactionDto, accountDto);
        Mockito.doNothing().when(transactionValidator).validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());

        Mockito.when(transactionRepository.save(transaction))
                .thenThrow(RuntimeException.class);

        Assert.assertThrows(RuntimeException.class, ()->transactionService.createTransactions(transactionDto));
        AccountDto savedAccountDto = accountService.getAccount(1L);
        Assert.assertNotNull(savedAccountDto);
        Assert.assertEquals(accountDto.getBalance(),savedAccountDto.getBalance());
    }

}
