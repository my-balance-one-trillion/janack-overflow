package com.example.janackoverflow.saving.repository;

import com.example.janackoverflow.saving.entity.InputAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InputAccountRepository extends JpaRepository<InputAccount, Long> {
    List<InputAccount> findByUsersId(long userId);
}
