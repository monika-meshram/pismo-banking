package com.pismo.accounts.rest;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.accounts.entity.Account;
import com.pismo.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts")
public class AccountController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;

	@Hidden
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		logger.info("Test Method");
		return "Working";
	}


	@Operation(
			summary = "Create Account",
			description = "Creates account with Document number.")
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "201",
							description = "Created",
							content = {
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = Long.class))
							}),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request",
							content =
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = String.class),
											examples =
											@ExampleObject("Document Number is mandatory, Account Id cannot be pre-selected, Account cannot be pre-credited"))),
					@ApiResponse(
							responseCode = "500",
							description = "Internal Server Error",
							content =
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = String.class),
											examples =
											@ExampleObject("Internal Server Error"))),
			})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createAccount(@RequestBody @Valid AccountDto account) {
		logger.info("Creating account for : " + account);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(accountService.createAccount(account));
	}

	@Operation(summary = "Get Account by Id", responses = {
			@ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			 })
	@GetMapping(value = "/{accountId}")
	public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId) {
		logger.info("Returning account details for accound Id : " + accountId);
		//return accountService.getAccount(accountId);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(accountService.getAccount(accountId));
	}
}
