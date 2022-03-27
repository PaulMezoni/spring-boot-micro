package com.javastart.account.dto;

import com.javastart.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccountResponseDto {
    private final Long accountId;
    private final String name;
    private final String email;
    private final String phone;
    private final OffsetDateTime creationDate;
    private final List<Long> bills;


    public AccountResponseDto(Account account) {
        this.accountId = account.getAccountId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.creationDate = account.getCreationDate();
        this.bills = account.getBills();
    }
}
