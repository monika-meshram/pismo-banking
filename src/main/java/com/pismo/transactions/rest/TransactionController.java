package com.pismo.transactions.rest;

import com.pismo.transactions.dto.TransactionDto;
import com.pismo.transactions.service.TransactionService;
import com.pismo.transactions.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		logger.info("Test Method");
		return "Working";
	}

	@PostMapping
	public TransactionDto createTransactions(@RequestBody TransactionDto transactionDto) {
		logger.info("Creating account for : " + transactionDto);
		return transactionService.createTransactions(transactionDto);
	}

	@GetMapping(value = "/{transactionId}")
	public TransactionDto getTransaction(@PathVariable Long transactionId) {
		logger.info("Getting transaction for id : " + transactionId);
		return transactionService.getTransaction(transactionId);
	}
}
