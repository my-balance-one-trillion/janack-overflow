package com.example.janackoverflow.saving.repository;

import com.example.janackoverflow.saving.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    Rule findByInputAccountId(Long accountId);
    Optional<Rule> findByInputAccountIdAndUsersId(Long accountId, Long usersId);
    Optional<Rule> findByUsersIdAndInputAccountId(Long userId, Long inputAccountId);
}
