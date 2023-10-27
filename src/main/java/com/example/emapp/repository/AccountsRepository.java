package com.example.emapp.repository;

import com.example.emapp.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {
}
