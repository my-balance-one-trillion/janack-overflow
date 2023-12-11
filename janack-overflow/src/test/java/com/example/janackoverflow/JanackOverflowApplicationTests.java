package com.example.janackoverflow;


import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.main.service.MainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class JanackOverflowApplicationTests {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private MainService mainService;
	@Test
	void contextLoads() {
		//issueRepository.findByUsersIdAndCreatedAtAfter(1L, LocalDateTime.now().minusWeeks(1))
				//.forEach(System.out::println);
		//System.out.println(issueRepository.countByUsersIdAndCreatedAtAfter(1L, LocalDateTime.now().minusDays(1)));
		//mainService.readWeeklyIssues(1l).forEach(System.out::println);
		System.out.println(mainService.readNowAccount(1L));
		//issueRepository.findTop10ByOrderByCreatedAtDesc().forEach(System.out::println);
	}


}
