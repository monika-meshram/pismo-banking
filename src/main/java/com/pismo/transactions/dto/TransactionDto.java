package com.pismo.transactions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
public class TransactionDto {

    @Schema(hidden = true)
    @Null(message = "Bad request - Transaction Id cannot be pre-selected")
    private Long transactionId;

    @NotNull(message = "Account ID is mandatory")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Account ID is mandatory")
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, description = "Account ID", example = "1")
    private Long accountId;

    @NotNull(message = "Operations Type ID is mandatory. Possible values 1,2,3,4")
    @Min(value = 1, message = "Operations Type ID is mandatory. Possible values 1,2,3,4")
    @Max(value = 4, message = "Operations Type ID is mandatory. Possible values 1,2,3,4")
    @Digits(integer = 1, fraction = 0, message = "Operations Type ID is mandatory. Possible values 1,2,3,4")
    @Schema(type = "integer", requiredMode = Schema.RequiredMode.REQUIRED, description = "Operation Type ID", example = "1,2,3,4")
    private int operationsTypeId;

    @NotNull(message = "Amount is mandatory")
    @Digits(integer = 4, fraction = 2, message = "Purchase/Withdrawal Amount should be less than 10000")
    @Schema(type = "BigDecimal", requiredMode = Schema.RequiredMode.REQUIRED, description = "Amount", example = "10000")
    private BigDecimal amount;

    @Schema(hidden = true)
    private LocalDateTime eventDate;
}
