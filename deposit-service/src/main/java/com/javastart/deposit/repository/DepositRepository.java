package com.javastart.deposit.repository;

import com.javastart.deposit.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long > {
}
