package com.example.janackoverflow.saving.repository;

import com.example.janackoverflow.saving.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    Rule findByInputAccountId(long accountId);
    Rule findByUsersId(Long id);
}
