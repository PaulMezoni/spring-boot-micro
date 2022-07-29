package com.javastart.depositservice.createEntity;

import com.javastart.deposit.dto.AccountResponseDto;
import com.javastart.deposit.dto.BillResponseDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class CreateEntity {
    public static AccountResponseDto createAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setBills(List.of(1L, 2L, 3L));
        accountResponseDto.setEmail("cat@cat.ru");
        accountResponseDto.setName("cat");
        accountResponseDto.setPhone("+7910");
        accountResponseDto.setCreationDate(OffsetDateTime.now());
        return accountResponseDto;
    }

    public static BillResponseDto createBillResponseDto() {
        BillResponseDto billResponseDto = new BillResponseDto();
        billResponseDto.setAccountId(1L);
        billResponseDto.setAmount(BigDecimal.valueOf(1300));
        billResponseDto.setBillId(1L);
        billResponseDto.setCreationDate(OffsetDateTime.now());
        billResponseDto.setIsDefault(true);
        billResponseDto.setOverdraftEnabled(true);
        return billResponseDto;
    }
}
