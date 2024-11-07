package com.pismo.transactions.rest;


import com.pismo.PismoBankingApplication;
import com.pismo.accounts.entity.Account;
import com.pismo.accounts.respository.AccountRepository;
import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.entity.Transaction;
import com.pismo.transactions.respository.TransactionRepository;
import com.pismo.transactions.service.TransactionService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;

@SpringBootTest(classes = PismoBankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();


    @Test
    public void addTransaction() {
        //Creating transaction related account
        Account account = Account.builder().documentNumber("123456").balance(BigDecimal.valueOf(000,2)).build();
        this.accountRepository.save(account);

        TransactionDto transactionDto = TransactionDto.builder().accountId(1L).amount(BigDecimal.ONE).operationsTypeId(4).build();

        HttpEntity<TransactionDto> entity = new HttpEntity<>(transactionDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transactions"),
                HttpMethod.POST, entity, String.class);

        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assert.assertEquals("1", response.getBody());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
