package com.burakkarahan.identityservice.repository;

import com.burakkarahan.identityservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByName(String username);
}