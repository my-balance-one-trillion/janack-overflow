package com.example.janackoverflow.saving.repository;

import com.example.janackoverflow.saving.entity.InputAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InputAccountRepository extends JpaRepository<InputAccount, Long> {
    Optional<InputAccount> findByUsersIdAndStatus(Long userId, String status);
    Optional<InputAccount> findTop1ByUsersIdAndStatusOrderByCreatedAtDesc(Long userId, String status);
    List<InputAccount> findByUsersIdOrderByCreatedAtDesc(Long id);
}
