package com.ustream_web3.repositories;

import com.ustream_web3.entities.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

    Optional<PasswordReset> findByToken(String token);
}
