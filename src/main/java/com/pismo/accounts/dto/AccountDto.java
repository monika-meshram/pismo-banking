package com.pismo.accounts.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class AccountDto {

    @Schema(hidden = true)
    @Null(message = "Bad request - Account Id cannot be pre-selected")
    private Long accountId;

    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, description = "Document number", example = "12345678900")
    @NotBlank(message = "Document number is mandatory")
    private String documentNumber;

    @Schema(hidden = true)
    @Null(message = "Bad request - Account cannot be pre-credited")
    private BigDecimal balance;

}
