package com.pismo.accounts.service.impl;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.mapper.AccountMapper;
import com.pismo.accounts.respository.AccountRepository;
import com.pismo.accounts.service.AccountService;
import com.pismo.exceptions.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pismo.accounts.entity.Account;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates an Account for the provided information - Document number
     * @param accountDto Consists of Document Number
     * @return Account ID of successfully created Account
     */
    @Override
    public Long createAccount(AccountDto accountDto) {
        Account account = AccountMapper.dtoToEntity(accountDto);
        account = accountRepository.save(account);
        accountDto.setAccountId(account.getAccountId());
        return accountDto.getAccountId();
    }

    /**
     * Return Account details for the provided AccountID
     * @param accoundId Integer value which is associated with the Account
     * @return Account details for Account ID
     */
    @Override
    public AccountDto getAccount(Long accoundId) {
        Optional<Account> account = accountRepository.findById(accoundId);
        if(account.isPresent()){
            return AccountMapper.entityToDto(account.get());
        } else {
            // We can throw an Exception here, to let user know that accound with ID doesnot exits.
            // OR without letting the user know that exact cause, just log it, we can return just the empty response(Security purpose)
            logger.error("Account with ID : " + accoundId + " does not exits.");
            throw new AccountNotFoundException("Account with ID : " + accoundId + " does not exits");
        }
    }

}
