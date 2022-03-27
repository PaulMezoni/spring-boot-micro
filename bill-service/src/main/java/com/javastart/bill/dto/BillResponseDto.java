package com.javastart.bill.dto;

import com.javastart.bill.entity.Bill;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class BillResponseDto {
    private final Long billId;

    private final Long accountId;
    private final BigDecimal amount;
    private final Boolean isDefault;
    private final OffsetDateTime creationDate;
    private final Boolean overdraftEnabled;

    public BillResponseDto(Bill bill) {
        this.billId = bill.getBillId();
        this.accountId = bill.getAccountId();
        this.amount = bill.getAmount();
        this.isDefault = bill.getIsDefault();
        this.creationDate = bill.getCreationDate();
        this.overdraftEnabled = bill.getOverdraftEnabled();
    }
}
