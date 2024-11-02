package com.pismo.accounts.mapper;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account dtoToEntity(AccountDto accountDto){
        return Account.builder()
                .documentNumber(accountDto.getDocumentNumber())
                .build();
    }

    public AccountDto entityToDto(Account account){
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .documentNumber(account.getDocumentNumber())
                .build();
    }
}
