package com.pismo.accounts.service;
import com.pismo.accounts.dto.AccountDto;

public interface AccountService {
    public Long createAccount(AccountDto account);
    public AccountDto getAccount(Long accoundId);
}
