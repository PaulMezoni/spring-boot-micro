package com.javastart.bill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class BillRequestDto {
    private final Long accountId;
    private final BigDecimal amount;
    private final Boolean isDefault;
    private final OffsetDateTime creationDate;
    private final Boolean overdraftEnabled;
}
