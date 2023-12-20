package com.example.janackoverflow.user.repository;

import com.example.janackoverflow.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByNickname(String nickname);

    Optional<Users> findByEmail(String email);

    Page<Users> findByOrderByCreatedAtDesc(Pageable pageable);

    // 최근 6개월간 각 월의 가입자 수
    @Query("SELECT MONTH(u.createdAt) as month, COUNT(u) as userCount FROM Users u WHERE u.createdAt BETWEEN :startDate AND :endDate GROUP BY MONTH(u.createdAt) ORDER BY MONTH(u.createdAt) DESC")
    List<Object[]> countUsersByMonthLast6Months(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
