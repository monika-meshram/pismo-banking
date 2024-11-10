package com.pismo.transactions.rest;

import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.service.TransactionService;
import com.pismo.transactions.entity.Transaction;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions")
public class TransactionController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TransactionService transactionService;

	@Operation(
			summary = " Creates a new transaction and associates it with respective Account",
			description = "Creates a new transaction with amount & Operation Type and associates it with respective Account")
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
	public ResponseEntity<Long> createTransactions(@RequestBody @Valid TransactionDto transactionDto) {
		logger.info("Creating account for : " + transactionDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(transactionService.createTransactions(transactionDto));

	}

	/*@Hidden
	@GetMapping(value = "/{transactionId}")
	public ResponseEntity<TransactionDto> getTransaction(@PathVariable Long transactionId) {
		logger.info("Getting transaction for id : " + transactionId);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(transactionService.getTransaction(transactionId));
	}*/
}
