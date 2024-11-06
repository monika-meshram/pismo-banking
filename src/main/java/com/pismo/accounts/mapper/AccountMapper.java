package com.pismo.accounts.mapper;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class AccountMapper {

    public static Account dtoToEntity(AccountDto accountDto){
        BigDecimal balance = Objects.isNull(accountDto.getAccountId()) ? BigDecimal.ZERO : accountDto.getBalance();
        return Account.builder()
                .accountId(accountDto.getAccountId())
                .documentNumber(accountDto.getDocumentNumber())
                .balance(balance)
                .build();
    }

    public static AccountDto entityToDto(Account account){
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .documentNumber(account.getDocumentNumber())
                .balance(account.getBalance())
                .build();
    }
}
