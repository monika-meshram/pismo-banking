package com.pismo.accounts.rest;

import com.pismo.PismoBankingApplication;
import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import com.pismo.accounts.respository.AccountRepository;
import com.pismo.accounts.service.AccountService;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@SpringBootTest(classes = PismoBankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private final Account account = Account.builder().documentNumber("123456").balance(BigDecimal.valueOf(000,2)).build();


    @BeforeEach
    public void beforeEach() {
       this.accountRepository.save(this.account);
    }

    @AfterEach
    public void afterEach() {
        this.accountRepository.deleteAll();
    }

    @Test
    public void testRetrieveAccount() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/accounts/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"accountId\":1,\"documentNumber\":\"123456\",\"balance\":0.00}";

        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCreateAccount() {

        AccountDto accountDto = AccountDto.builder().documentNumber("12345").build();

        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/accounts"),
                HttpMethod.POST, entity, String.class);

        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assert.assertEquals("2", response.getBody());
        Assert.assertNotNull(response.getBody());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}