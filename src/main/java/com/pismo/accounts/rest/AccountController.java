package com.pismo.accounts.rest;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import com.pismo.accounts.service.AccountService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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
	public ResponseEntity<Long> createAccount(@RequestBody @Valid AccountDto account) {
		logger.info("Creating account for : " + account);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(accountService.createAccount(account));
		//return new ResponseEntity<>(accountService.createAccount(account).getAccountId(), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{accountId}")
	public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId) {
		logger.info("Returning account details for accound Id : " + accountId);
		//return accountService.getAccount(accountId);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(accountService.getAccount(accountId));
	}
}
