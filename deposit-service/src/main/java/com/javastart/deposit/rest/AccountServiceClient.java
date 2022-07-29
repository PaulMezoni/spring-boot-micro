package com.javastart.deposit.rest;

import com.javastart.deposit.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping("accounts/{accountId}")
    AccountResponseDto getAccountById(@PathVariable("accountId") Long accountId);
}
