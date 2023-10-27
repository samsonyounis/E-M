package com.example.emapp.repository;

import com.example.emapp.models.Transactionss;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactionss, Long> {
}
