package com.javastart.depositservice.service;

import com.javastart.deposit.dto.DepositResponseDto;
import com.javastart.deposit.exception.DepositServiceException;
import com.javastart.deposit.repository.DepositRepository;
import com.javastart.deposit.rest.AccountServiceClient;
import com.javastart.deposit.rest.BillServiceClient;
import com.javastart.deposit.service.DepositService;
import com.javastart.depositservice.createEntity.CreateEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;

import static com.javastart.depositservice.createEntity.CreateEntity.createAccountResponseDto;
import static com.javastart.depositservice.createEntity.CreateEntity.createBillResponseDto;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {
    @Mock
    private DepositRepository depositRepository;
    @Mock
    private AccountServiceClient accountServiceClient;
    @Mock
    private BillServiceClient billServiceClient;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @InjectMocks
    private DepositService depositService;

    @Test(expected = DepositServiceException.class)
    public void DepositServiceTest_exception() {
        depositService.deposit(null, null, BigDecimal.valueOf(1000));
    }

    @Test
    public void DepositServiceTest_withBuildId() {
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(createBillResponseDto());
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(createAccountResponseDto());
        DepositResponseDto deposit = depositService.deposit(null, 1L, BigDecimal.valueOf(1300));

        Assertions.assertThat(deposit.getEmail()).isEqualTo("cat@cat.ru");
        Assertions.assertThat(deposit.getAmount()).isEqualTo(BigDecimal.valueOf(1300));
    }

}
