package com.pismo.transactions.validations;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.exceptions.AccountNotFoundException;
import com.pismo.exceptions.InappropriateAmountException;
import com.pismo.exceptions.InsufficientBalanceException;
import com.pismo.exceptions.OperationNotFoundException;
import com.pismo.transactions.constant.OperationType;
import com.pismo.transactions.dto.TransactionDto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class TransactionValidator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void validateTransactionAccount(TransactionDto transactionDto, AccountDto account){
        //Validate if account exits
        if(Objects.isNull(account.getAccountId())){
            throw new AccountNotFoundException("Account with ID : " + transactionDto.getAccountId() + " does not exit.");
        }
        // Check for amount - account.getAmount();
    }

    public void validateOperationType(int operationTypeId, BigDecimal amount) {
        if(OperationType.isDebitOperationType(operationTypeId)){
            logger.info("Operation type is Debit with id : " + operationTypeId);
            // Check if amount is negative else throw exception
            if(amount.signum() != -1){
                logger.info("For Operation type id : " + operationTypeId + " amount must be negative");
                throw new InappropriateAmountException("For Operation type id : " + operationTypeId + " amount must be negative");
            }
            // Check if totalamount - amount >= 0 else throw exception
        } else if (OperationType.isCreditOperationType(operationTypeId)){
            logger.info("Operation type is Credit with id : " + operationTypeId);
            // Check if amount is positive else throw exception
            if(amount.signum() != 1){
                logger.info("For Operation type id : " + operationTypeId + " amount must be positive");
                throw new InappropriateAmountException("For Operation type id : " + operationTypeId + " amount must be positive");
            }
        } else {
            throw new OperationNotFoundException("Operation with ID : " + operationTypeId + " does not exit.");
        }
    }

    public void validateAccountBalanceForTransaction(TransactionDto transactionDto, AccountDto accountDto) {
        BigDecimal balancePostTransaction = accountDto.getBalance().add(transactionDto.getAmount());
        //balacePostTransaction should never be negative value
        if(BigDecimal.ZERO.compareTo(balancePostTransaction) > 0){
            logger.info("Insufficient funds in Account " +accountDto.getAccountId()+ " for this transaction.");
            throw new InsufficientBalanceException("Insufficient funds in Account " +accountDto.getAccountId()+ " for this transaction.");
        }
    }
}
