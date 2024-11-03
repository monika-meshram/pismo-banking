package com.pismo.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountDto {
    private Long accountId;

    @NotBlank(message = "Document number is mandatory")
    private String documentNumber;

}
