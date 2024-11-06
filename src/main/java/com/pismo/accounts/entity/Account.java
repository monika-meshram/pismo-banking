package com.pismo.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    @NotNull
    private String documentNumber;

    @NotNull
    @NotNull(message = "Amount is mandatory")
    @Digits(integer = 40, fraction = 2, message = "Purchase/Withdrawal Amount should be less than 10000")
    private BigDecimal balance;
}
