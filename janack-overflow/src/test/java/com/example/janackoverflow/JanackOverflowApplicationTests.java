package com.example.janackoverflow;


import com.example.janackoverflow.chat.controller.ChatMessageController;
import com.example.janackoverflow.chat.domain.ChatMessageDTO;
import com.example.janackoverflow.chat.entity.ChatMessage;
import com.example.janackoverflow.chat.entity.ChatRoom;
import com.example.janackoverflow.chat.entity.ChatRoomUsers;
import com.example.janackoverflow.chat.repository.ChatMessageRepository;
import com.example.janackoverflow.chat.repository.ChatRoomRepository;
import com.example.janackoverflow.chat.repository.ChatRoomUsersRepository;
import com.example.janackoverflow.chat.service.ChatMessageService;
import com.example.janackoverflow.chat.service.ChatRoomService;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.main.service.BankingService;
import com.example.janackoverflow.main.service.MainService;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
class JanackOverflowApplicationTests {

	@Autowired
	ChatMessageService chatMessageService;
	@Autowired
	ChatRoomService chatRoomService;
	@Autowired
	ChatMessageController chatMessageController;

	@Autowired
	ChatRoomRepository chatRoomRepository;
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	ChatRoomUsersRepository chatRoomUsersRepository;
	@Autowired
	BankingService bankingService;
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Test
	void contextLoads() throws JsonProcessingException {
//		System.out.println(chatMessageRepository.findTop1ByChatRoomIdAndUsersIdAndTypeOrderByCreatedAtDesc(43L, 31L, ChatMessageDTO.MessageType.ENTER)
//				.get().getId());
		chatMessageService.readChatSinceJoin(43L, 31L)
				.forEach(System.out::println);
	}


}
