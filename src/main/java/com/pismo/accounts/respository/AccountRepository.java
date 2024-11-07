package com.pismo.accounts.respository;

import com.pismo.accounts.entity.Account;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

@Hidden
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<Account> findById(Long id);

    public Account save(Account account);
}
