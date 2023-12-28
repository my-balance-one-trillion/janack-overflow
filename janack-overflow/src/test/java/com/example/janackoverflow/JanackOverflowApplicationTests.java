package com.example.janackoverflow;


import com.example.janackoverflow.chat.repository.ChatMessageRepository;
import com.example.janackoverflow.chat.repository.ChatRoomRepository;
import com.example.janackoverflow.chat.repository.ChatRoomUsersRepository;
import com.example.janackoverflow.chat.service.ChatMessageService;
import com.example.janackoverflow.chat.service.ChatRoomService;
import com.example.janackoverflow.main.service.BankingService;
import com.example.janackoverflow.user.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JanackOverflowApplicationTests {

	@Autowired
	ChatMessageService chatMessageService;
	@Autowired
	ChatRoomService chatRoomService;
//	@Autowired
//	ChatMessageController chatMessageController;

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

		bankingService.transfer(10000, "3020000009693");
	}


}
