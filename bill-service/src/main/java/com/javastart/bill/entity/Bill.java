package com.javastart.bill.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bill_id", nullable = false)
    private Long billId;

    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private OffsetDateTime creationDate;
    private Boolean overdraftEnabled;

    public Bill(Long accountId, BigDecimal amount, Boolean isDefault,
                OffsetDateTime creationDate, Boolean overdraftEnabled) {
        this.accountId = accountId;
        this.amount = amount;
        this.isDefault = isDefault;
        this.creationDate = creationDate;
        this.overdraftEnabled = overdraftEnabled;
    }

    public Bill(Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        this.accountId = accountId;
        this.amount = amount;
        this.isDefault = isDefault;
        this.overdraftEnabled = overdraftEnabled;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", isDefault=" + isDefault +
                ", creationDate=" + creationDate +
                ", overdraftEnabled=" + overdraftEnabled +
                '}';
    }
}
