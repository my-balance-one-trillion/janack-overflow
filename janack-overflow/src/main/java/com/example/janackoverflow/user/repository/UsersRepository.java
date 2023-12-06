package com.example.janackoverflow.user.repository;

import com.example.janackoverflow.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
