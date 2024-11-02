package com.pismo.accounts.service;
import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;

public interface AccountService {
    public AccountDto createAccount(AccountDto account);
    public AccountDto getAccount(Long accoundId);
}
