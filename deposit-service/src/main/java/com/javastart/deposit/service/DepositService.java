package com.javastart.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.deposit.dto.DepositResponseDto;
import com.javastart.deposit.entity.Deposit;
import com.javastart.deposit.exception.DepositServiceException;
import com.javastart.deposit.repository.DepositRepository;
import com.javastart.deposit.rest.*;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {
    public static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    public static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository depositRepository;
    private final BillServiceClient billServiceClient;
    private final AccountServiceClient accountServiceClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DepositService(DepositRepository depositRepository, BillServiceClient billServiceClient, AccountServiceClient accountServiceClient, RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.billServiceClient = billServiceClient;
        this.accountServiceClient = accountServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @SneakyThrows
    public DepositResponseDto deposit(Long accountId, Long billId, BigDecimal amount) {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("Account and Bill is null");
        }
        if (billId != null) {
            BillResponseDto billResponseDto = billServiceClient.getBillById(billId);
            BillRequestDto billRequestDto = createBillRequest(amount, billResponseDto);

            billServiceClient.update(billId, billRequestDto);

            AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(billResponseDto.getAccountId());
            depositRepository.save(new Deposit(amount, billId, OffsetDateTime.now(), accountResponseDto.getEmail()));

            return createResponse(amount, accountResponseDto);
        }
        BillResponseDto defaultBill = getDefaultBill(accountId);
        BillRequestDto billRequestDto = createBillRequest(amount, defaultBill);
        billServiceClient.update(defaultBill.getBillId(), billRequestDto);
        AccountResponseDto account = accountServiceClient.getAccountById(accountId);
        depositRepository.save(new Deposit(amount, defaultBill.getBillId(), OffsetDateTime.now(), account.getEmail()));
        return createResponse(amount, account);
    }

    private DepositResponseDto createResponse(BigDecimal amount, AccountResponseDto accountResponseDto) {
        DepositResponseDto depositResponseDto = new DepositResponseDto(amount, accountResponseDto.getEmail());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                    objectMapper.writeValueAsString(depositResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Can't send message to RabbitMQ");
        }
        return depositResponseDto;
    }

    private BillRequestDto createBillRequest(BigDecimal amount, BillResponseDto billResponseDto) {
        BillRequestDto billRequestDto = new BillRequestDto();
        billRequestDto.setAccountId(billResponseDto.getAccountId());
        billRequestDto.setCreationDate(billResponseDto.getCreationDate());
        billRequestDto.setIsDefault(billResponseDto.getIsDefault());
        billRequestDto.setOverdraftEnabled(billResponseDto.getOverdraftEnabled());
        billRequestDto.setAmount(billResponseDto.getAmount().add(amount));
        return billRequestDto;
    }

    private BillResponseDto getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId).stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Unable to find bill for account: " + accountId));
    }

}
