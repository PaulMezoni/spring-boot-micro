package com.javastart.deposit.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {
    @RequestMapping(value = "bills/{billId}", method = RequestMethod.GET)
    BillResponseDto getBillById(@PathVariable("billId") Long billId);

    @RequestMapping(value = "bills/{billId}", method = RequestMethod.PUT)
    void update(@PathVariable("billId") Long billId, BillRequestDto billRequestDTO);

    @RequestMapping(value = "bills/account/{accountId}", method = RequestMethod.GET)
    List<BillResponseDto> getBillsByAccountId(@PathVariable("accountId") Long accountId);

//    @GetMapping("bills/{billId}")
//    BillResponseDto getBillById(@PathVariable("billId") Long billId);
//
//    @PutMapping("/bills/{billId}")
//    void update(@PathVariable("billId") Long billId, BillRequestDto billRequestDto);
//
//    @GetMapping("bills/account/{accountId}")
//    List<BillResponseDto> getBillsByAccountId(@PathVariable("accountId") Long accountId);
}
