package com.javastart.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.notification.config.RabbitMQConfig;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DepositMessageHandler {
    private final JavaMailSender javaMailSender;

    @Autowired
    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @SneakyThrows
    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) {
        System.out.println(message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(jsonBody, DepositResponseDto.class);
        System.out.println(depositResponseDto);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(depositResponseDto.getMail());
        mailMessage.setFrom("lori@cat.xyz");

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit, sum: " + depositResponseDto.getAmount());
        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            String exception = e.getMessage();
            System.out.println(exception);
        }
    }
}
