package com.pismo.accounts.rest;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import com.pismo.accounts.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		logger.info("Test Method");
		return "Working";
	}

	@PostMapping
	public Long createAccount(@RequestBody AccountDto account) {
		logger.info("Creating account for : " + account);
		return accountService.createAccount(account).getAccountId();
	}

	@GetMapping(value = "/{accountId}")
	public AccountDto getAccount(@PathVariable Long accountId) {
		logger.info("Returning account details for accound Id : " + accountId);
		return accountService.getAccount(accountId);
	}
}
