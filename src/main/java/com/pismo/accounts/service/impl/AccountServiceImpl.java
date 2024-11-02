package com.pismo.accounts.service.impl;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.mapper.AccountMapper;
import com.pismo.accounts.respository.AccountRepository;
import com.pismo.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pismo.accounts.entity.Account;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.dtoToEntity(accountDto);
        account = accountRepository.save(account);
        accountDto.setAccountId(account.getAccountId());
        return accountDto;
    }

    @Override
    public AccountDto getAccount(Long accoundId) {
        Account account = accountRepository.findById(accoundId).get();
        return accountMapper.entityToDto(account);
    }

}
