package com.javastart.deposit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity@Getter@Setter@NoArgsConstructor@ToString
public class Deposit {
    @Id
    @Column(name = "deposit_id", nullable = false)
    private Long depositId;

    private BigDecimal amount;
    private Long billId;
    private OffsetDateTime creationDate;
    private String email;

    public Deposit(BigDecimal amount, Long billId, OffsetDateTime creationDate, String email) {
        this.amount = amount;
        this.billId = billId;
        this.creationDate = creationDate;
        this.email = email;
    }
}
