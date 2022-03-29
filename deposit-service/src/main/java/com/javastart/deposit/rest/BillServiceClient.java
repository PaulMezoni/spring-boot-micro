package com.javastart.deposit.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {
    @GetMapping("bills/{billId}")
    BillResponseDto getBillById(@PathVariable("billId") Long billId);

    @PutMapping("/bills/{billId}")
    void update(@PathVariable("billId") Long billId, BillRequestDto billRequestDto);

    @GetMapping("bills/account/{accountId}")
    List<BillResponseDto> getBillsByAccountId(@PathVariable("accountId") Long accountId);
}
