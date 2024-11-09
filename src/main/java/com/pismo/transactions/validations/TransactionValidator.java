package com.pismo.transactions.validations;

import com.pismo.accounts.dto.AccountDto;
import com.pismo.exceptions.AccountNotFoundException;
import com.pismo.exceptions.InappropriateAmountException;
import com.pismo.exceptions.InsufficientBalanceException;
import com.pismo.exceptions.OperationNotFoundException;
import com.pismo.transactions.constant.OperationType;
import com.pismo.transactions.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class TransactionValidator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Validator method to validate if the account exits
     * @param transactionDto
     * @param account
     */
    public void validateTransactionAccount(TransactionDto transactionDto, AccountDto account){
        if(Objects.isNull(account.getAccountId())){
            throw new AccountNotFoundException("Account with ID : " + transactionDto.getAccountId() + " does not exit.");
        }
        // Check for amount - account.getAmount();
    }

    /**
     * Validator method to validate if the Operation Type and Amount provided by the customer are aligned.
     *
     * @param operationTypeId
     * @param amount
     *
     * @exception InappropriateAmountException If the OperationTypeId is for Normal Purchase, Purchase with installment or Debit and the Amount is Positive
     * and if the OperationTypeId Credit and the Amount is negative
     * @exception OperationNotFoundException If the OperationTypeId is not valid
     */
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

    /**
     * Validator method to check if the balance after completing a transaction will be Positive or Negative
     * @param transactionDto
     * @param accountDto
     *
     * @exception InsufficientBalanceException If the balance after transaction will be Negative
     */
    public void validateAccountBalanceForTransaction(TransactionDto transactionDto, AccountDto accountDto) {
        BigDecimal balancePostTransaction = accountDto.getBalance().add(transactionDto.getAmount());
        //balacePostTransaction should never be negative value
        if(BigDecimal.ZERO.compareTo(balancePostTransaction) > 0){
            logger.info("Insufficient funds in Account " +accountDto.getAccountId()+ " for this transaction.");
            throw new InsufficientBalanceException("Insufficient funds in Account " +accountDto.getAccountId()+ " for this transaction.");
        }
    }
}
