package com.pismo.transactions.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionDto {
    private Long transactionId;
    private int accountId;
    private int operationsTypeId;
    private float amount;
    private Date eventDate;
}
