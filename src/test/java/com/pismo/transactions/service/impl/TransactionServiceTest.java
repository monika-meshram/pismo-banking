package com.pismo.transactions.service.impl;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.service.AccountService;
import com.pismo.exceptions.AccountNotFoundException;
import com.pismo.exceptions.InappropriateAmountException;
import com.pismo.exceptions.InsufficientBalanceException;
import com.pismo.exceptions.OperationNotFoundException;
import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import com.pismo.transactions.respository.TransactionRepository;
import com.pismo.transactions.service.TransactionService;
import com.pismo.transactions.validations.TransactionValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private TransactionValidator transactionValidator;

    @MockBean
    private AccountService accountService;

    @Test
    void testCreateTransaction_Ok(){
       AccountDto accountDto = AccountDto.builder().accountId(1L).documentNumber("12345").balance(BigDecimal.valueOf(0L, 2)).build();
       TransactionDto transactionDto = TransactionDto.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();
       Transaction transaction = Transaction.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();
       Transaction savedtransaction = Transaction.builder().transactionId(1L).accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();

       Mockito.when(accountService.getAccount(transactionDto.getAccountId())).thenReturn(accountDto);
       Mockito.doNothing().when(transactionValidator).validateAccountBalanceForTransaction(transactionDto, accountDto);
       Mockito.doNothing().when(transactionValidator).validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());
       Mockito.when(accountService.createAccount(accountDto)).thenReturn(1L);
       Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(savedtransaction);

       Long transactionId = transactionService.createTransactions(transactionDto);

       Assert.assertEquals(Long.valueOf(1), transactionId);
    }

    @Test
    void testCreateTransaction_AccountNotFound(){
        TransactionDto transactionDto = TransactionDto.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();

        Mockito.when(accountService.getAccount(transactionDto.getAccountId())).thenThrow(AccountNotFoundException.class);

        Assert.assertThrows(AccountNotFoundException.class, () -> transactionService.createTransactions(transactionDto));
    }

    @Test
    void testCreateTransaction_InvalidOperationTypeInvalidAmount() {
        AccountDto accountDto = AccountDto.builder().accountId(1L).documentNumber("12345").balance(BigDecimal.valueOf(0L, 2)).build();
        TransactionDto transactionDto = TransactionDto.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(2).build();

        Mockito.doNothing().when(transactionValidator).validateAccountBalanceForTransaction(transactionDto, accountDto);

        Mockito.doThrow(InappropriateAmountException.class)
                .when(transactionValidator)
                .validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());

        Assert.assertThrows(InappropriateAmountException.class, () -> transactionService.createTransactions(transactionDto));

    }

    @Test
    void testCreateTransaction_InvalidOperationTypeOperationNotFound() {
        AccountDto accountDto = AccountDto.builder().accountId(1L).documentNumber("12345").balance(BigDecimal.valueOf(0L, 2)).build();
        TransactionDto transactionDto = TransactionDto.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(5).build();

        Mockito.when(accountService.getAccount(transactionDto.getAccountId())).thenReturn(accountDto);
        Mockito.doNothing().when(transactionValidator).validateAccountBalanceForTransaction(transactionDto, accountDto);
        Mockito.doThrow(OperationNotFoundException.class)
                .when(transactionValidator)
                .validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());

        Assert.assertThrows(OperationNotFoundException.class, () -> transactionService.createTransactions(transactionDto));

    }

    @Test
    void testCreateTransaction_InsufficientAmount(){
        AccountDto accountDto = AccountDto.builder().accountId(1L).documentNumber("12345").balance(BigDecimal.valueOf(0L, 2)).build();
        TransactionDto transactionDto = TransactionDto.builder().transactionId(1L).accountId(1L).amount(BigDecimal.valueOf(-100L, 2)).operationsTypeId(1).build();

        Mockito.when(accountService.getAccount(transactionDto.getAccountId())).thenReturn(accountDto);
        Mockito.doNothing().when(transactionValidator).validateOperationType(transactionDto.getOperationsTypeId(), transactionDto.getAmount());
        Mockito.doThrow(InsufficientBalanceException.class)
                .when(transactionValidator)
                .validateAccountBalanceForTransaction(transactionDto, accountDto);

        Assert.assertThrows(InsufficientBalanceException.class, () -> transactionService.createTransactions(transactionDto));
    }
}

