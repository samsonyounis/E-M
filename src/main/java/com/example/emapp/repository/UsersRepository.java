package com.example.emapp.repository;

import com.example.emapp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUserByEmail(String username);

}
