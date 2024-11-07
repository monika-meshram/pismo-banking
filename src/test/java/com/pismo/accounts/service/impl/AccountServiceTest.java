package com.pismo.accounts.service.impl;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import com.pismo.accounts.respository.AccountRepository;
import com.pismo.accounts.service.AccountService;
import com.pismo.exceptions.AccountNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @MockBean
    AccountRepository accountRepository;

    @Test
    void createAccount_Ok() {
        AccountDto accountDto = AccountDto.builder().documentNumber("12345").build();
        Account savedAccount = Account.builder().accountId(1L).documentNumber("12345").balance(BigDecimal.ONE).build();

        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(savedAccount);

        Long savedAccoundId = accountService.createAccount(accountDto);

        Assert.assertEquals(Long.valueOf(1), savedAccoundId);
    }

    @Test
    void testGetAccount_Ok() {
        Account account = Account.builder().accountId(1L).documentNumber("12345").balance(BigDecimal.valueOf(0L, 2)).build();

        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));

        AccountDto accountDto = accountService.getAccount(1L);

        Assert.assertNotNull(accountDto);
    }

    @Test
    void testGetAccount_AccountNotFound(){
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Assert.assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(1L));
    }
}